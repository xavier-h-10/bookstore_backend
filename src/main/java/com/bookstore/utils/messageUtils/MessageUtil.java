package com.bookstore.utils.messageUtils;

import net.sf.json.JSONObject;

public class MessageUtil {

  public static final String LOGIN_ERROR_MSG = "密码或用户名有误，请重新输入！";
  public static final String LOGIN_BAN_MSG ="此用户已被禁用！";
  public static final String LOGIN_SUCCESS_MSG = "登陆成功！";
  public static final String ALREADY_LOGIN_MSG = "用户已经登陆。";
  public static final String NOT_LOGIN_MSG = "用户未登陆！";
  public static final String LOGOUT_SUCCESS_MSG = "退出登录成功！";
  public static final String LOGOUT_ERROR_MSG = "退出登录失败！";
  public static final String REGISTER_ERROR_MSG = "此用户名已注册，请更换其他用户名！";
  public static final String REGISTER_SUCCESS_MSG = "注册成功!";
  public static final String NOT_ALLOW_MSG="当前用户非管理员，不能访问此页面。";


  public static final int LOGIN_ERROR_CODE = -1;
  public static final int LOGIN_SUCCESS_CODE = 1;
  public static final int NOT_LOGIN_CODE = -2;
  public static final int ALREADY_LOGIN_CODE = 0;
  public static final int LOGOUT_SUCCESS_CODE = 2;
  public static final int LOGOUT_ERROR_CODE = -3;
  public static final int REGISTER_ERROR_CODE = -4;
  public static final int REGISTER_SUCCESS_CODE = 4;
  public static final int NOT_ALLOW_CODE=5;

  public static Message createMessage(int statusCode, String message) {
    return new Message(statusCode, message);
  }

  public static Message createMessage(int statusCode, String message, JSONObject data) {
    return new Message(statusCode, message, data);
  }
}
