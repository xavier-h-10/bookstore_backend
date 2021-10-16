package com.bookstore.utils.solrUtils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrUtil {

  public static SolrClient getSolrClient() {
    final String solrUrl = "http://localhost:8983/solr/book_collection";
    return new HttpSolrClient.Builder(solrUrl)
        .withConnectionTimeout(10000)
        .withSocketTimeout(60000)
        .build();
  }
}
