package com.bookstore.serviceimpl;

import com.bookstore.dao.UserDao;
import com.bookstore.entity.User;
import com.bookstore.entity.UserAuth;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  final UserDao userDao;

  @Autowired
  UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserAuth checkAuth(String username, String userPassword) {
    return userDao.checkAuth(username, userPassword);
  }

  @Override
  public User getUser() {
    return userDao.getUser();
  }

  @Override
  public User getUserById(Integer userId) {
    return userDao.getUserById(userId);
  }


  @Override
  public boolean register(String username, String password, String name, String email) {
    return userDao.register(username, password, name, email);
  }

  @Override
  public List<User> getAllUsers() { return userDao.getAllUsers(); }

  @Override
  public void updateUserStatus(Integer userId,Boolean enabled) {
    userDao.updateUserStatus(userId,enabled);
  }

  @Override
  public boolean registerCheck(String username) {
    return userDao.registerCheck(username);
  }
}
