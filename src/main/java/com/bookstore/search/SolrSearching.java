package com.bookstore.search;

import com.bookstore.entity.BookInfo;
import com.bookstore.utils.solrUtils.SolrUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

@Slf4j
public class SolrSearching {

  public static List<BookInfo> getBooksByKeyword(String keyword) {
    SolrClient client = SolrUtil.getSolrClient();
    final Map<String, String> queryParamMap = new HashMap<String, String>();

    queryParamMap.put("q", "description:" + keyword);
    queryParamMap.put("fl", "id, description, name");
    queryParamMap.put("sort", "id asc");
    MapSolrParams queryParams = new MapSolrParams(queryParamMap);
    List<BookInfo> res = new ArrayList<>();
    try {
      final QueryResponse response = client.query("book_collection", queryParams);
      final SolrDocumentList documents = response.getResults();
      log.info("Found " + documents.getNumFound() + " documents");
      for (SolrDocument document : documents) {
        final String id = (String) document.getFirstValue("id");
        final String description = (String) document.getFirstValue("description");
        final String name = (String) document.getFirstValue("name");
        BookInfo book = new BookInfo(id, description, name);
        res.add(book);
        System.out.println("id: " + id + "; name: " + name);
      }
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
    return res;
  }
}
