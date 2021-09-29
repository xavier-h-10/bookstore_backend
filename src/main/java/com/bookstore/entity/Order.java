package com.bookstore.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Order{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="order_id")
  private Integer orderId;

  @ManyToOne(fetch= FetchType.EAGER)
  @JoinColumn(name="user_id",referencedColumnName = "user_id")
  private User user;

  private Timestamp time;

  private BigDecimal price;

  @OneToMany(fetch=FetchType.EAGER,mappedBy = "orderId")
  private List<OrderItem> orderItem;

}





