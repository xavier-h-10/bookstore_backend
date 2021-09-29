package com.bookstore.websockets.decoders;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.json.Json;
import com.bookstore.websockets.messages.Message;
import com.bookstore.websockets.messages.ChatMessage;
import javax.json.stream.JsonParser;

//只考虑chatMessage 20210928
public class MessageDecoder implements Decoder.Text<Message> {
  /* Stores the name-value pairs from a JSON message as a Map */
  private Map<String,String> messageMap;

  @Override
  public void init(EndpointConfig ec) { }

  @Override
  public void destroy() { }

  /* Create a new Message object if the message can be decoded */
  @Override
  public Message decode(String string) throws DecodeException {
    Message msg = null;
    if (willDecode(string)) {
          msg = new ChatMessage(messageMap.get("message"));
      }

     else {
      throw new DecodeException(string, "[Message] Can't decode.");
    }
    return msg;
  }

  /* Decode a JSON message into a Map and check if it contains
   * all the required fields according to its type. */
  @Override
  public boolean willDecode(String string) {
    boolean decodes = false;
    /* Convert the message into a map */
    messageMap = new HashMap<>();
    JsonParser parser = Json.createParser(new StringReader(string));
    while (parser.hasNext()) {
      if (parser.next() == JsonParser.Event.KEY_NAME) {
        String key = parser.getString();
        parser.next();
        String value = parser.getString();
        messageMap.put(key, value);
      }
    }

    //此处信息只要包含message即可
    Set keys = messageMap.keySet();
//    String[] chatMsgKeys = {"name", "message"};
    String[] chatMsgKeys={"message"};
    if (keys.containsAll(Arrays.asList(chatMsgKeys))) {
      decodes = true;
    }
    return decodes;
  }
}
