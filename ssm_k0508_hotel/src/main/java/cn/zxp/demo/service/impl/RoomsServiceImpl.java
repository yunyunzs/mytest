package cn.zxp.demo.service.impl;

import cn.zxp.demo.pojo.Rooms;
import cn.zxp.demo.service.RoomsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *  @Description TODO  客房信息的实现类
 */
@Service
@Transactional(readOnly = false)
public class RoomsServiceImpl extends BaseServiceImpl<Rooms> implements RoomsService {

}
