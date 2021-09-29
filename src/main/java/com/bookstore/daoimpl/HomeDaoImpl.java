package com.bookstore.daoimpl;

import com.bookstore.dao.HomeDao;
import com.bookstore.repository.HomeRepository;
import com.bookstore.entity.HomeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HomeDaoImpl implements HomeDao {

  HomeRepository homeRepository;

  @Autowired
  void setHomepageRepository(HomeRepository homeRepository) {
    this.homeRepository = homeRepository;
  }

  @Override
  public List<HomeItem> getHomeContent() {
    System.out.println("homepage dao executed");
    System.out.println(homeRepository.getHomeContent());
    return homeRepository.getHomeContent();
  }
}
