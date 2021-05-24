package cn.zxp.demo.service.impl;

import cn.zxp.demo.dao.InRoomInfoMapper;
import cn.zxp.demo.dao.OrdersMapper;
import cn.zxp.demo.dao.RoomSaleMapper;
import cn.zxp.demo.dao.RoomsMapper;
import cn.zxp.demo.pojo.InRoomInfo;
import cn.zxp.demo.pojo.Orders;
import cn.zxp.demo.pojo.RoomSale;
import cn.zxp.demo.pojo.Rooms;
import cn.zxp.demo.service.OrdersService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *  @Description TODO  订单业务实现类
 */
@Service
@Transactional(readOnly = false)
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private InRoomInfoMapper inRoomInfoMapper;

    @Autowired
    private RoomsMapper roomsMapper;

    @Autowired
    private RoomSaleMapper roomSaleMapper;

    //订单业务层方法重写父类的保存数据方法
    @Override
    public String saveT(Orders orders) {
        //1.生成订单数据(以订单的添加为主)
        int insOrdersCount = ordersMapper.insert(orders);
        //2.入住信息是否退房的状态的修改（未退房-->已退房）  0 -> 1
        //新建要被修改的入住信息对象
        InRoomInfo inRoomInfo = new InRoomInfo();
        //往要被修改的入住信息中设置值
        inRoomInfo.setId(orders.getIriId());
        inRoomInfo.setOutRoomStatus("1");
        //执行入住信息的修改
        int upInRoomInfoCount = inRoomInfoMapper.updateByPrimaryKeySelective(inRoomInfo);
        //自定义异常，对事物进行测试
        //int i = 1/0;
        //3.客房的状态修改（已入住-->打扫）  1 -> 2
        //根据入住信息id查询出入住信息
        InRoomInfo selInRoomInfo = inRoomInfoMapper.selectByPrimaryKey(orders.getIriId());
        //新建客房对象
        Rooms rooms=new Rooms();
        //往要被修改的客房信息中设置值
        rooms.setId(selInRoomInfo.getRoomId());
        rooms.setRoomStatus("2");
        int upRoomsCount = roomsMapper.updateByPrimaryKeySelective(rooms);
        if (insOrdersCount>0&&upInRoomInfoCount>0&&upRoomsCount>0){
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    public String afterOrdersPay(String out_trade_no) throws Exception {
        //1.根据订单编号查询单个订单数据
        //1.1.新建订单查询的条件对象
        Orders parOrders = new Orders();
        //1.2.将订单编号设置进去
        parOrders.setOrderNum(out_trade_no);
        //1.3.执行条件查询单个数据
        Orders orders = ordersMapper.selTByParams(parOrders);
        //2.修改订单支付状态 由0（未结算）---->1（已结算）
        //2.1.新建修改的订单对象
        Orders updOrders = new Orders();
        //2.2.将订单主键id和状态设置进去
        updOrders.setId(orders.getId());
        updOrders.setOrderStatus("1");
        //2.3.执行动态修改
        int updOrdersCount = ordersMapper.updateByPrimaryKeySelective(updOrders);
        //3.完成销售记录的添加
        //3.1.新建销售记录对象
        RoomSale roomSale = new RoomSale();

        //3.2.往此对象中设置数据
        String[] orderOther = parOrders.getOrderOther().split(",");
        //设置房间号
        roomSale.setRoomNum(orderOther[0]);
        //设置客户名称
        roomSale.setCustomerName(orderOther[1]);
        //设置入住时间
        roomSale.setStartDate(DateUtils.parseDate(orderOther[2],new String[]{"yyyy/MM/dd HH:mm:ss"}));
        //设置退房时间
        roomSale.setEndDate(DateUtils.parseDate(orderOther[3],new String[]{"yyyy/MM/dd HH:mm:ss"}));
        //设置入住天数
        roomSale.setDays(Integer.valueOf(orderOther[4]));
        //2-2 : 获取order_price字段，通过,号分割字符串，得到数据
        String[] orderPrice = parOrders.getOrderPrice().split(",");
        //客房单价
        roomSale.setRoomPrice(Double.valueOf(orderPrice[0]));
        //其它消费
        roomSale.setOtherPrice(Double.valueOf(orderPrice[1]));
        //实际的住房金额
        roomSale.setRentPrice(Double.valueOf(orderPrice[2]));
        //订单的实际支付金额(订单的支付总金额)
        roomSale.setSalePrice(orders.getOrderMoney());
        //优惠金额（客房单价*天数-实际的住房金额）
        Double discountPrice = roomSale.getRoomPrice()*roomSale.getDays()-roomSale.getRentPrice();
        roomSale.setDiscountPrice(discountPrice);
        //3.3.执行消费记录的添加
        int insRoomSaleCount = roomSaleMapper.insert(roomSale);
        if(updOrdersCount>0&&insRoomSaleCount>0){
            return "redirect:/model/toHome";  //重定向到首页
        }else {
            return "redirect:/model/toErrorPay";  //重定向到异常页
        }
    }
}
