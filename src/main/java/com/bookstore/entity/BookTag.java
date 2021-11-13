package com.bookstore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Set;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity("tag")
public class BookTag {

  @Id
  @GeneratedValue
  private Long id;

  @Property("tagId")
  private String tagId;

  @Property("name")
  private String name;

}
