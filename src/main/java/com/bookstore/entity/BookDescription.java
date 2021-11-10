package com.bookstore.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "book_description")
public class BookDescription {

  @Id
  private Integer id;

  private String description;
}
