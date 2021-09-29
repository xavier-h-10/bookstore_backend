package com.bookstore.controller;

import com.bookstore.utils.messageUtils.Message;
import com.bookstore.utils.messageUtils.MessageUtil;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bookstore.entity.HomeItem;
import com.bookstore.service.HomeService;

import java.util.List;

@RestController
public class HomeController {
    HomeService homeService;
    private AtomicInteger total=new AtomicInteger(0);

    @Autowired
    void setHomepageService(HomeService homeService)
    {
        this.homeService=homeService;
    }

    @ResponseBody
    @RequestMapping("/getHomeContent")
    public List<HomeItem> getHomeContent()
    {
        System.out.println("home controller executed");
        return homeService.getHomeContent();
    }

    //多线程访问统计量 20210928
    //已采用jMeter进行多线程测试
    @RequestMapping("/getPageView")
    public synchronized Message getPageView() {
        Integer res = total.incrementAndGet();
        JSONObject object = new JSONObject();
        object.put("total", res);
        return MessageUtil.createMessage(200, "成功访问页面", object);
    }
}
