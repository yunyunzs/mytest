package cn.zxp.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图跳转的控制器
 */
@Controller
@RequestMapping("/model")
public class ModelController {
    //跳转到项目的首页页面
    @RequestMapping("toHome")
    public String showEmpUI(){
        return "home";
    }
    //跳转到入住信息查询页面
    @RequestMapping("toShowInRoomInfo")
    public String toShowInRoomInfo(){
        return "inRoomInfo/showInRoomInfo";
    }

    //跳转到入住信息添加页面
    @RequestMapping("toSaveInRoomInfo")
    public String toSaveInRoomInfo(){
        return "inRoomInfo/saveInRoomInfo";
    }

    //去到订单显示页面
    @RequestMapping("toShowOrders")
    public String toShowOrders(){
        return "orders/showOrders";
    }

    //去到支付的页面
    @RequestMapping("/toOrdersPay")
    public String toOrdersPay(){
        return "alipay/ordersPay";
    }

    //支付回调失败去到异常页面
    @RequestMapping("/toErrorPay")
    public String toErrorPay(){
        return "alipay/errorPay";
    }

    //去到消费记录页面
    @RequestMapping("/toShowRoomSale")
    public String toShowRoomSale(){
        return "roomSale/showRoomSale";
    }

    //去到会员查询页面
    @RequestMapping("/toShowVip")
    public String toShowVip(){
        return "vip/showVip";
    }

    //去到会员添加页面
    @RequestMapping("/toSaveVip")
    public String toSaveVip(){
        return "vip/saveVip";
    }

    //去到客房显示页面
    @RequestMapping("/toShowRooms")
    public String toShowRooms(){
        return "rooms/showRooms";
    }

    //去到房型显示页面
    @RequestMapping("/toShowRoomType")
    public String toShowRoomType(){
        return "roomType/showRoomType";
    }

    //去到登录显示页面
    @RequestMapping("/toLoginUI")
    public String toLoginUI(){
        return "login/login";
    }

    //去到数据分析显示页面
    @RequestMapping("/toShowDbi")
    public String toShowDbi(){
        return "dbi/showDbi";
    }
}
