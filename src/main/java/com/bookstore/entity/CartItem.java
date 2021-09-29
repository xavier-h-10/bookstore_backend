package com.bookstore.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
class PrimaryKey_CartItem implements Serializable {

  private Integer userId;

  private Integer bookId;
}

@Data
@Entity
@Table(name = "cart_item")
@IdClass(PrimaryKey_CartItem.class)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class CartItem{
  @Id
  private Integer userId;

  @Id
  @Column(name="book_id",insertable = false, updatable = false)
  private Integer bookId;
  private Integer amount;
  private Boolean active;

  @ManyToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="book_id",referencedColumnName = "book_id")
  private Book book;

}
