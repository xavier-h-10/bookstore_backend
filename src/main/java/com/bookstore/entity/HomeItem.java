package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="home")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class HomeItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer imageId;
  private String image;
}
