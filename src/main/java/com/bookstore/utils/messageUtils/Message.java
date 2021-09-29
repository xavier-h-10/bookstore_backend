package com.bookstore.utils.messageUtils;

import net.sf.json.JSONObject;

public class Message {
    private int status;
    private String message;
    private JSONObject data;

    Message(int status, String message, JSONObject data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    Message(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public JSONObject getData() {
        return data;
    }
}
