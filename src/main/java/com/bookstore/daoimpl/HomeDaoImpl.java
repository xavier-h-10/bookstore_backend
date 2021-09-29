package com.bookstore.daoimpl;

import com.alibaba.fastjson.TypeReference;
import com.bookstore.dao.HomeDao;
import com.bookstore.repository.HomeRepository;
import com.bookstore.entity.HomeItem;
import com.bookstore.utils.redisUtils.RedisUtil;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

@Repository
@Slf4j
public class HomeDaoImpl implements HomeDao {

  HomeRepository homeRepository;
  RedisUtil redisUtil;

  @Autowired
  void setHomepageRepository(HomeRepository homeRepository) {
    this.homeRepository = homeRepository;
  }

  @Autowired
  void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  @Override
  public List<HomeItem> getHomeContent() {
    System.out.println("homepage dao executed");
    Object p = redisUtil.get("homeContent");
    List<HomeItem> res = null;
    if (p == null) {
      log.info("Get homeContent from database.");
      res = homeRepository.getHomeContent();
      redisUtil.set("homeContent", JSONArray.toJSON(res));
    } else {
      log.info("Get homeContent from Redis.");
      res = JSONArray.parseObject(p.toString(), new TypeReference<List<HomeItem>>() {
      });
    }
    return res;
  }
}
