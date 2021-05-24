package cn.zxp.demo.dao;

import cn.zxp.demo.pojo.RoomSale;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoomSaleMapper extends BaseMapper<RoomSale>{
    //查询房间的销售金额,返回List
    @Select("SELECT room_num roomNum,SUM(sale_price) sumPrice from roomsale GROUP BY room_num")
    List<Map<String, Object>> selPriceByRoomNum();
}