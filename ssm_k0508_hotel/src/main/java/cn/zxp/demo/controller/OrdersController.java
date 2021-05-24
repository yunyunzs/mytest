package cn.zxp.demo.controller;

import cn.zxp.demo.pojo.Orders;
import cn.zxp.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("orders")
public class OrdersController extends BaseController<Orders> {
    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/afterOrdersPay")
    public String afterOrdersPay(String out_trade_no){
        //在业务层完成上述的操作
        try {
            return ordersService.afterOrdersPay(out_trade_no);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
