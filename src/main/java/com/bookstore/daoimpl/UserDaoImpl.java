package com.bookstore.daoimpl;

import com.bookstore.dao.UserDao;
import com.bookstore.entity.User;
import com.bookstore.entity.UserAuth;
import com.bookstore.repository.UserAuthRepository;
import com.bookstore.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

  UserAuthRepository userAuthRepository;
  UserRepository userRepository;

  @Autowired
  public void setUserAuthRepository(UserAuthRepository userAuthRepository) {
    this.userAuthRepository = userAuthRepository;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserAuth checkAuth(String username, String userPassword) {
    return userAuthRepository.checkAuth(username, userPassword);
  }

  @Override
  public User getUser() {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    if (servletRequestAttributes != null) {
      HttpServletRequest request = servletRequestAttributes.getRequest();
      HttpSession session = request.getSession(false);
      if (session != null) {
        Integer userId = (Integer) session.getAttribute("userId");
        return userRepository.getUserById(userId);
      }
    }
    return null;
  }

  @Override
  public User getUserById(Integer userId) {
    return userRepository.getUserById(userId);
  }

  @Override
  public boolean register(String username, String password, String name, String email) {
    UserAuth Auth = userAuthRepository.getUserAuthByUsername(username);
    if (Auth == null) {
      userRepository.addUser(name, email);
      User nowUser = userRepository.getUserByName(name);
      System.out.println(nowUser);
      userAuthRepository.addUserAuth(nowUser.getUserId(), username, password, 0);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  @Override
  public void updateUserStatus(Integer userId,Boolean enabled) {
    userRepository.updateUserStatus(userId,enabled);
  }

  @Override
  public boolean registerCheck(String username) {
    System.out.println("registerCheck dao called, username="+username);
    Integer res=userAuthRepository.registerCheck(username);
    if(res>0) return true; else return false;
  }
}
