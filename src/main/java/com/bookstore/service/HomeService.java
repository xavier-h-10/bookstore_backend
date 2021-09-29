package com.bookstore.service;

import com.bookstore.entity.HomeItem;

import java.util.List;

public interface HomeService {
    List<HomeItem> getHomeContent();
}
