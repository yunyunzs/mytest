package cn.zxp.demo.dao;

import cn.zxp.demo.pojo.RoleAuth;

public interface RoleAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAuth record);

    int insertSelective(RoleAuth record);

    RoleAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAuth record);

    int updateByPrimaryKey(RoleAuth record);
}