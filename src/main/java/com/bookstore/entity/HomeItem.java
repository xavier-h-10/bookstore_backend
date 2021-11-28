package com.bookstore.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "home_item")
public class HomeItem {
  @Id
  private String id;

  private Integer imageId;

  private String image;
}
