package com.bookstore.entity;


import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@AllArgsConstructor
@Data
public class BookInfo {

  private String Id;

  private String description;

  private String name;
}
