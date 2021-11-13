package com.bookstore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Set;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;


@Data
@NodeEntity("book")
public class BookNode {

  @Id
  @GeneratedValue
  private Long id;

  @Property("bookId")
  private String bookId;

  @Property("name")
  private String name;

  @Relationship(type = "related")
  public Set<BookTag> neighbors;
}
