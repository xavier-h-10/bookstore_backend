package com.bookstore.interceptor;


import com.bookstore.utils.messageUtils.Message;
import com.bookstore.utils.messageUtils.MessageUtil;
import com.bookstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionValidateInterceptor extends HandlerInterceptorAdapter {
    public SessionValidateInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        boolean status = SessionUtil.checkAuth();
        if (!status) {
            System.out.println("preHandle Failed");
            Message message = MessageUtil.createMessage(MessageUtil.NOT_LOGIN_CODE, MessageUtil.NOT_LOGIN_MSG);
            this.sendJsonBack(response, message);
            return false;
        } else {
            System.out.println("preHandle Success");
            return true;
        }
    }

    private void sendJsonBack(HttpServletResponse response, Message message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSONObject.fromObject(message));
        } catch (IOException e) {
            System.out.println("send json back error");
        }
    }
}


//在HandlerInterceptorAdapter中主要提供了以下的方法：
//    preHandle：在方法被调用前执行。在该方法中可以做类似校验的功能。如果返回true，则继续调用下一个拦截器
//    。如果返回false，则中断执行，也就是说我们想调用的方法不会被执行，但是你可以修改response为你想要的响应。
//    postHandle：在方法执行后调用。
//    afterCompletion：在整个请求处理完毕后进行回调，也就是说视图渲染完毕或者调用方已经拿到响应。