package com.bookstore.serviceimpl;

import com.bookstore.entity.HomeItem;
import com.bookstore.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.bookstore.dao.HomeDao;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    HomeDao homeDao;

    @Autowired
    void setHomepageDao(HomeDao homeDao){
        this.homeDao=homeDao;
    }

    @Override
    public List<HomeItem> getHomeContent() {
        return homeDao.getHomeContent();
    }
}
