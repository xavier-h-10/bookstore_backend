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

  @Query(value="match (na:tag{tagId:$tagId})--()-->(nb:tag) match (nc:book)-->(nd:tag) where nd=na or nd=nb return nc;")
  List<BookNode> findRelatedBooks(@Param("tagId") String tagId);
}
