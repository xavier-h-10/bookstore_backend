package com.bookstore.repository;

import com.bookstore.entity.BookNode;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookNodeRepository extends Neo4jRepository<BookNode, Long> {
  BookNode findBookNodeByName(String name);

  @Query(value="match (na:tag)--()-->(nb:tag) where na.tagId in $tags match (nc:book)-->(nd:tag) where nd=na or nd=nb return distinct nc")
  List<BookNode> findRelatedBooks(@Param("tags") List<String> tags);


}
