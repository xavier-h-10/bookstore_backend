package com.bookstore.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
class PrimaryKey_OrderItem implements Serializable {

  private Integer orderId;

  private Integer bookId;
}

@Data
@Entity
@Table(name = "order_item")
@IdClass(PrimaryKey_OrderItem.class)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "bookId")
public class OrderItem{
  @Id
  private Integer orderId;

  @Id
  @Column(name="book_id",insertable = false, updatable = false)
  private Integer bookId;
  private Integer amount;
//  private Double price;

  @ManyToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="book_id",referencedColumnName = "book_id")
  //@NotFound(action= NotFoundAction.IGNORE)
  private Book book;

}
