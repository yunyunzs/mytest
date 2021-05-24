package cn.zxp.demo.service.impl;

import cn.zxp.demo.dao.InRoomInfoMapper;
import cn.zxp.demo.dao.RoomsMapper;
import cn.zxp.demo.pojo.InRoomInfo;
import cn.zxp.demo.pojo.Rooms;
import cn.zxp.demo.service.InRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName InRoomInfoServiceImpl
 * @Description TODO 入住信息的实现类
 */
@Service
@Transactional(readOnly = false)
public class InRoomInfoServiceImpl extends BaseServiceImpl<InRoomInfo> implements InRoomInfoService {
    @Autowired
    private InRoomInfoMapper inRoomInfoMapper;

    @Autowired
    private RoomsMapper roomsMapper;

    //重写添加入住信息方法
    @Override
    public String saveT(InRoomInfo inRoomInfo){
        //1.执行入住信息添加
        Integer insINICount = inRoomInfoMapper.insert(inRoomInfo);
        //2.完成客房状态修改
        //2.1.新建客房对象
        Rooms rooms = new Rooms();
        //2.2.设置客房对象的数据
        rooms.setId(inRoomInfo.getRoomId());
        rooms.setRoomStatus("1");
        //2.3.执行客房状态修改 由0（空闲）---->1（以入住）
        int updRoomsCount = roomsMapper.updateByPrimaryKeySelective(rooms);
        if(insINICount>0&&updRoomsCount>0){
            return "success";
        }else {
            return "fail";
        }
    }
}
