package com.bookstore.dao;

import com.bookstore.entity.HomeItem;

import java.util.List;

public interface HomeDao {
  List<HomeItem> getHomeContent();
}

