import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

import org.apache.hadoop.fs.FileSystem;

public class KeywordCount {

  public static class TokenizerMapper
      extends Mapper<Object, Text, Text, IntWritable> {

    static enum CountersEnum {INPUT_WORDS}

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    private boolean caseSensitive;
    private boolean keywordMode; //mode=true则为统计关键词模式，否则为统计所有单词
    private Set<String> keywordSet = new HashSet<String>(); // 用来保存需过滤的关键词，从配置文件中读出赋值

    private Configuration conf;
    private BufferedReader fis;

    @Override
    public void setup(Context context) throws IOException,
        InterruptedException {
      conf = context.getConfiguration();
      caseSensitive = conf.getBoolean("wordcount.case.sensitive", false); //设置对大小写不敏感
      if (conf.getBoolean("wordcount.keyword.patterns", false)) {
        keywordMode = true;
        URI[] keywords = Job.getInstance(conf).getCacheFiles();
        for (URI keyword : keywords) {
          Path keywordPath = new Path(keyword.getPath());
          String keywordFileName = keywordPath.getName().toString();
          parseKeywordFile(keywordFileName);
        }
      }
    }

    private void parseKeywordFile(String fileName) {
      try {
        fis = new BufferedReader(new FileReader(fileName));
        String pattern = null;
        while ((pattern = fis.readLine()) != null) {
          keywordSet.add(pattern);
        }
      } catch (IOException ioe) {
        System.err.println("Caught exception while parsing the cached file '"
            + StringUtils.stringifyException(ioe));
      }
    }

    @Override
    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {
      String line = (caseSensitive) ?
          value.toString() : value.toString().toLowerCase();  //转化为小写

      line = line.replaceAll("[0-9]+", "");
      line = line.replaceAll("\"", "");
      line = line.replaceAll("[(),:.?]", "");  //使用正则表达式，去除数字、引号、括号等字符

      StringTokenizer itr = new StringTokenizer(line);
      while (itr.hasMoreTokens()) {
        String nowToken = itr.nextToken();
        if ((keywordMode && keywordSet.contains(nowToken)) || !keywordMode) {
          word.set(nowToken);
          context.write(word, one);
          Counter counter = context.getCounter(CountersEnum.class.getName(),
              CountersEnum.INPUT_WORDS.toString());
          counter.increment(1);
        }
      }
    }
  }

  public static class IntSumReducer
      extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
        Context context
    ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
    String[] remainingArgs = optionParser.getRemainingArgs();
    // remainingArgs.length == 2时，包括输入输出路径：
    //   /user/wordcount/input /user/wordcount/output
    // remainingArgs.length == 4时，包括输入输出路径和关键词文件：
    //   /user/wordcount/input /user/wordcount/output -key /user/wordcount/patterns.txt
    if ((remainingArgs.length != 2) && (remainingArgs.length != 4)) {
      System.err.println("Usage: wordcount <in> <out> [-key keywordFile]");
      System.exit(2);
    }
    Job job = Job.getInstance(conf, "keyword count");

    job.setJarByClass(KeywordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    job.setNumReduceTasks(1);

    List<String> otherArgs = new ArrayList<String>();
    for (int i = 0; i < remainingArgs.length; ++i) {
      if ("-key".equals(remainingArgs[i])) {
        job.addCacheFile(new Path(remainingArgs[++i]).toUri());
        job.getConfiguration().setBoolean("wordcount.keyword.patterns", true);
      } else {
        otherArgs.add(remainingArgs[i]);
      }
    }
    FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));

    FileSystem fs = FileSystem.get(URI.create(otherArgs.get(1)), conf);
    fs.delete(new Path(otherArgs.get(1)), true); //删除输出文件夹,防止无法输出
    FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));


    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
