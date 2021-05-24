package cn.zxp.demo.service.impl;

import cn.zxp.demo.pojo.Vip;
import cn.zxp.demo.service.VipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class VipServiceImpl extends BaseServiceImpl<Vip> implements VipService {

}
