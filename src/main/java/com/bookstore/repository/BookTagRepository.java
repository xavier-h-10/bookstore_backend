package com.bookstore.repository;

import com.bookstore.entity.BookTag;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookTagRepository extends Neo4jRepository<BookTag, Long> {
  List<BookTag> findAll();

  @Query(value="match (na:tag{tagId:$tagId})--()-->(nb:tag) return na,nb")
  List<BookTag> findRelatedTags(@Param("tagId") String tagId);

}
