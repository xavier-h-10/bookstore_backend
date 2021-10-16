package com.bookstore.utils.fileUtils;

import com.alibaba.fastjson.JSONArray;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

  public static JSONArray ConvertToJsonArray(String path) {

    JSONArray jsonArray = null;
    BufferedReader reader = null;
    StringBuilder jsonStrs = new StringBuilder();
    InputStream inputStream = null;
    try {
      try {
        inputStream = new FileInputStream(path);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      InputStreamReader inputStreamReader = null;
      try {
        inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      reader = new BufferedReader(inputStreamReader);
      String tempStr = null;
      while (true) {
        try {
          if (!((tempStr = reader.readLine()) != null)) {
            break;
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        jsonStrs.append(tempStr);
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    log.info(jsonStrs.toString());
    try {
      jsonArray = JSONArray.parseArray(jsonStrs.toString().trim());
    } catch (IllegalStateException ex) {
      log.error(path + "JSON  File is wrong");
    }
    return jsonArray;
  }
}
