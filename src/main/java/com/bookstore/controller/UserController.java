package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.entity.UserAuth;
import com.bookstore.service.UserService;
import com.bookstore.utils.messageUtils.Message;
import com.bookstore.utils.messageUtils.MessageUtil;
import com.bookstore.utils.sessionUtils.SessionUtil;
import java.util.Map;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/checkAuth")
  UserAuth checkAuth(@RequestParam("userAccount") String userAccount,
      @RequestParam("userPassword") String userPassword) {
    return userService.checkAuth(userAccount, userPassword);
  }

  @RequestMapping("/getUser")
  User getUser() {
    return userService.getUser();
  }

  @RequestMapping("/getUserById")
  User getUserById(@RequestParam("userId") Integer userId) {
    return userService.getUserById(userId);
  }

  /* 管理员获取所有用户,管理权限 */
  @RequestMapping("/getAllUsers")
  List<User> getAllUsers() {
    JSONObject Auth = SessionUtil.getAuth();
    if (Auth == null) {
      return null;
    }
    Integer isAdmin = Auth.getInt("userType");
    if (isAdmin == 0) {
      return null;
    }
    return userService.getAllUsers();
  }

  /* 管理员禁用，解禁用户的状态*/
  @RequestMapping("/updateUserStatus")
  void updateUserStatus(@RequestParam("userId") Integer userId,
      @RequestParam("enabled") Boolean enabled) {
    userService.updateUserStatus(userId, enabled);
  }


  @RequestMapping("/register")
  public Message register(@RequestBody Map<String, String> params) {
    String username = params.get("username");
    String password = params.get("password");
    String email = params.get("email");
    String name = params.get("name");
    boolean status = userService.register(username, password, name, email);

    if (status) {
      return MessageUtil
          .createMessage(MessageUtil.REGISTER_SUCCESS_CODE, MessageUtil.REGISTER_SUCCESS_MSG);
    } else {
      return MessageUtil
          .createMessage(MessageUtil.REGISTER_ERROR_CODE, MessageUtil.REGISTER_ERROR_MSG);
    }
  }

  @RequestMapping("/registerCheck")
  public boolean registerCheck(@RequestParam("username") String username) {
    return userService.registerCheck(username);
  }


  @RequestMapping("/checkSession")
  public Message checkSession() {
    JSONObject Auth = SessionUtil.getAuth();
    System.out.println("checkSession: " + Auth);
    if (Auth != null) {
      Integer isAdmin = Auth.getInt("userType");
      if (isAdmin == 0) {
        return MessageUtil
            .createMessage(MessageUtil.NOT_ALLOW_CODE, MessageUtil.NOT_ALLOW_MSG);
      } else {
        return MessageUtil
            .createMessage(MessageUtil.ALREADY_LOGIN_CODE, MessageUtil.ALREADY_LOGIN_MSG);
      }
    } else {
      return MessageUtil.createMessage(MessageUtil.NOT_LOGIN_CODE, MessageUtil.NOT_LOGIN_MSG);
    }
  }


}
