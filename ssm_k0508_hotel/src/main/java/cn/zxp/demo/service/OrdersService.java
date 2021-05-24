package cn.zxp.demo.service;

import cn.zxp.demo.pojo.Orders;

public interface OrdersService extends BaseService<Orders>{

    String afterOrdersPay(String out_trade_no) throws Exception;
}
