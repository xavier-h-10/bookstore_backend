package com.bookstore.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

  @Bean
  public SolrClient solrClient() {
    final String solrUrl = "http://localhost:8983/solr";
    return new HttpSolrClient.Builder(solrUrl)
        .withConnectionTimeout(10000)
        .withSocketTimeout(60000)
        .build();
  }
}
