package com.bookstore.websockets.messages;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.Data;

@Data
public class UsersMessage extends Message {

  private List<String> userList;
  private String time;

  public UsersMessage(List<String> list) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    this.userList = list;
    this.time = df.format(System.currentTimeMillis());
  }

  /* For logging purposes */
  @Override
  public String toString() {
    return "[UsersMessage] " + time+" "+userList.toString();
  }

  @Override
  public String toJSONObject() {
    JSONObject object = new JSONObject();
    try {
      object.put("message", userList);
      object.put("type", 2);
      object.put("time",time);
      object.put("description", "UsersMessage");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object.toString();
  }

}
