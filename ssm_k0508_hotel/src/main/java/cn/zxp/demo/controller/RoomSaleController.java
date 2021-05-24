package cn.zxp.demo.controller;

import cn.zxp.demo.pojo.RoomSale;
import cn.zxp.demo.service.RoomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("roomSale")
public class RoomSaleController extends BaseController<RoomSale> {
    @Autowired
    private RoomSaleService roomSaleService;
    /**
     *   加载客房销售数据
     * @return  图形加载的数据
     * @throws Exception
     */
    @RequestMapping("/loadRoomSale")
    public @ResponseBody Map<String,Object> loadRoomSale(){
        try {
            return roomSaleService.findRoomSale();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
