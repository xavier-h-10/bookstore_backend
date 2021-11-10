package com.bookstore.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "book_image")
public class BookImage {

  @Id
  private Integer id;

  private String image;
}
