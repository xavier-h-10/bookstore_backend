package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "book")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Integer bookId;

  private String name;

  private String author;

  private BigDecimal price;

  private String isbn;

  private Integer inventory;

  @Transient
  private String description;

  @Transient
  private String image;

  private String type;

  private String brief;

  private Boolean enabled;

  public Book(String name, String author, BigDecimal price, String isbn, Integer inventory,
      String type, String brief) {
    this.name = name;
    this.author = author;
    this.price = price;
    this.isbn = isbn;
    this.inventory = inventory;
    this.type = type;
    this.brief = brief;
    this.enabled = true;
  }
}
