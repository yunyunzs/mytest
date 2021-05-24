package cn.zxp.demo.service;

import cn.zxp.demo.pojo.RoomSale;

import java.util.Map;

public interface RoomSaleService extends BaseService<RoomSale>{
    /**
     *   加载客房销售数据
     * @return  图形加载的数据
     */
    Map<String, Object> findRoomSale();
}
