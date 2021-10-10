package com.bookstore.controller;

import com.bookstore.utils.messageUtils.Message;
import com.bookstore.utils.messageUtils.MessageUtil;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bookstore.entity.HomeItem;
import com.bookstore.service.HomeService;

import java.util.List;

@RestController
@Slf4j
public class HomeController {
    HomeService homeService;
    //默认认为网站没有那么大的访问量，因此采用AtomicInteger存
    private static AtomicInteger total=new AtomicInteger(0);

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
    //已采用jMeter进行多线程测试,在2000并发量下访问正常
    @RequestMapping("/getPageView")
    public static synchronized Message getPageView() {
        Integer res = total.incrementAndGet();
        JSONObject object = new JSONObject();
        object.put("total", res);
        log.info("receive request count={}",res);  //调试用,检验正确性
        return MessageUtil.createMessage(200, "成功访问页面", object);
    }
}
