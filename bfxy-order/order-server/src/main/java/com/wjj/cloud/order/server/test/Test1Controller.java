package com.wjj.cloud.order.server.test;

import com.wjj.cloud.order.server.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: wangjunjie 2019/3/22 15:27
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/22 15:27
 */

@RequestMapping("/test1")
@RestController
public class Test1Controller {

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/test")
    public String testOrder() {
        int result = orderMapper.updateOrderStatus("8848b", "3", "wjj", new Date());
        if (result==1) {
            return "success";
        }else {
            return "false";
        }
    }

    @PostMapping("/test2")
    public String test2(String id,String name,int age) {
        System.out.println("..id.."+id+"..name...."+name+"..age..."+age);
        String str= "..id.."+id+"..name...."+name+"..age..."+age;
        return str;
    }

    @PostMapping("/test3")
    public String test3(TestParam testParam) {
        System.out.println("..id.."+testParam.getId()+"..name...."+testParam.getName()+"..age..."+testParam.getAge());
        String str= "..id.."+testParam.getId()+"..name...."+testParam.getName()+"..age..."+testParam.getAge();
        return str;
    }
}
