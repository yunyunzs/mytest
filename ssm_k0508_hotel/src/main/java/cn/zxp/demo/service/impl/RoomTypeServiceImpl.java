package cn.zxp.demo.service.impl;

import cn.zxp.demo.pojo.RoomType;
import cn.zxp.demo.service.RoomTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class RoomTypeServiceImpl extends BaseServiceImpl<RoomType> implements RoomTypeService {

}
