package com.bookstore.repository;

import com.bookstore.entity.HomeItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HomeRepository extends JpaRepository<HomeItem,Integer> {
  @Query(value="from HomeItem")
  List<HomeItem> getHomeContent();
}

