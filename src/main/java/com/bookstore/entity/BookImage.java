package com.bookstore.entity;

import javax.persistence.Column;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "book_image")
public class BookImage {

  @Id
  private ObjectId id;

  @Column(name = "book_id")
  private Integer bookId;

  private String image;
}
