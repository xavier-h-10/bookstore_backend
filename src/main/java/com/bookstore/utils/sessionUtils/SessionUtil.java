package com.bookstore.utils.sessionUtils;

import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void setSession(JSONObject data) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取Request实例
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            System.out.println("set session. HttpSession Id: "+session.getId());
            for (Object thisKey : data.keySet()) {   //返回所有key的列表
                String key = (String) thisKey;
                Object val = data.get(key);
                session.setAttribute(key, val);
                System.out.println("set attribute key: "+key+" val: "+val);
            }
        }
    }

    public static boolean removeSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                return true;
            }
        }
        return false;
    }

    public static Boolean checkAuth() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Integer userIdentity = (Integer) session.getAttribute("userType");
                return userIdentity != null && userIdentity >= 0;
            }
        }
        return false;
    }

    public static JSONObject getAuth() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                JSONObject AuthObject = new JSONObject();
                AuthObject.put("userId", (Integer) session.getAttribute("userId"));
                AuthObject.put("username", (String) session.getAttribute("username"));
                AuthObject.put("userType", (Integer) session.getAttribute("userType"));
                return AuthObject;
            }
        }
        return null;
    }

    public static Integer getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null)
                return (Integer) session.getAttribute("userId");
        }
        return null;
    }
}
