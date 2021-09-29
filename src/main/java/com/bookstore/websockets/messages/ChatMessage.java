package com.bookstore.websockets.messages;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ChatMessage extends Message {

  private String name;
  //target字段暂时保留，便于以后扩充
  private String target;
  private String message;
  private boolean isMine;
  //发送时更新时间
  private String time;
//
//  public ChatMessage(String name, String target, String message) {
//    this.name = name;
//    this.target = target;
//    this.message = message;
//    this.isMine = false;
//    this.time = "";
//  }
//
//  public ChatMessage(String name, String message) {
//    this.name = name;
//    this.target = "";
//    this.message = message;
//    this.isMine = false;
//    this.time = "";
//  }

  public ChatMessage(String message) {
    this.name = "";
    this.target = "";
    this.message = message;
    this.isMine = false;
    this.time = "";
  }

  /* For logging purposes */
  @Override
  public String toString() {
    return "[ChatMessage] " + name + "-" + target + "-" + message;
  }

  @Override
  public String toJSONObject() {
    JSONObject object = new JSONObject();
    try {
      object.put("name", name);
      object.put("target", target);
      object.put("message", message);
      object.put("isMine", isMine);
      object.put("time", time);
      object.put("type", 3);
      object.put("description", "ChatMessage");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object.toString();
  }

}
