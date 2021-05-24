package cn.zxp.demo.service.impl;

import cn.zxp.demo.pojo.User;
import cn.zxp.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

}
