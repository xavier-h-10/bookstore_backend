package com.bookstore.websockets.messages;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import lombok.Data;

@Data
public class InfoMessage extends Message {

  private String info;
  private String time;

  public InfoMessage(String info) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    this.info = info;
    this.time = df.format(System.currentTimeMillis());
  }

  /* For logging purposes */
  @Override
  public String toString() {
    return "[InfoMessage] " + info+" "+time;
  }

  @Override
  public String toJSONObject() {
    JSONObject object = new JSONObject();
    try {
      object.put("message", info);
      object.put("type", 1);
      object.put("time",time);
      object.put("description", "InfoMessage");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object.toString();
  }

}
