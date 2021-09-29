package com.bookstore.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class OrderStat {
  private Integer bookId;
  private String name;
  private Integer amount;
  private BigDecimal price;
}
