//中文分词器配置 https://blog.csdn.net/weixin_40787926/article/details/89487114
package com.bookstore.search;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bookstore.controller.BookController;
import com.bookstore.entity.BookInfo;
import com.bookstore.utils.fileUtils.JsonUtil;
import com.bookstore.utils.solrUtils.SolrUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SolrIndexing {

  @Autowired
  BookController bookController;

  public static final String DIR = "./src/main/resources/search/";


  //从文件中建立索引
  public static void indexingFromFile() throws IOException, SolrServerException {
    final SolrClient client = SolrUtil.getSolrClient();
    JSONArray data = JsonUtil.ConvertToJsonArray(DIR + "info.json");
    List<BookInfo> list = JSONObject.parseArray(data.toJSONString(), BookInfo.class);
    int len = list.size();
    log.info("index: get " + len + " books");
    for (int i = 0; i < len; i++) {
      BookInfo bookInfo = list.get(i);
      SolrInputDocument doc = new SolrInputDocument();
      doc.addField("id", bookInfo.getId());
      doc.addField("name", bookInfo.getName());
      doc.addField("description", bookInfo.getDescription());
      log.info("add book: id=" + bookInfo.getId() + " name=" + bookInfo.getName());
      UpdateResponse updateResponse = client.add(doc);
      client.commit();
    }
  }

  public static void main(String[] args) {
    try {
      indexingFromFile();
    } catch (IOException | SolrServerException e) {
      e.printStackTrace();
    }
  }


}

//solr可直接在solrconfig.xml中配置mysql集成索引,因此舍弃下面的方法
//直接从数据库中取数据,建立索引
//  public void indexingFromDatabase() throws IOException, SolrServerException {
//    final SolrClient client = SolrUtil.getSolrClient();
//
//    JSONArray data = bookController.exportBookInfo();
//    List<BookInfo> list = JSONObject.parseArray(data.toJSONString(), BookInfo.class);
//    int len = list.size();
//    for (int i = 0; i < len; i++) {
//      BookInfo bookInfo = list.get(i);
//      SolrInputDocument doc = new SolrInputDocument();
//      doc.addField("id", bookInfo.getId());
//      doc.addField("name", bookInfo.getName());
//      doc.addField("description", bookInfo.getDescription());
//      log.info("add book: id=" + bookInfo.getId() + " name=" + bookInfo.getName());
//      UpdateResponse updateResponse = client.add(doc);
//      client.commit();
//    }
//  }
