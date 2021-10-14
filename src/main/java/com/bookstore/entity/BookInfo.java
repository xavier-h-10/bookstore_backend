package com.bookstore.entity;


import javax.persistence.Id;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "book_collection")
@Data
public class BookInfo {

  @Id
  @Field
  private String Id;

  @Field
  private String description;

  @Field
  private String name;
}
