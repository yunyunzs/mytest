package cn.zxp.demo.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    //嵌套查询 - 分页查询所有数据
    List<T> selAll();

    //嵌套查询 - 根据条件分页查询数据
    List<T> selectPageParams(T t);

    // 根据多个主键id批量删除数据
    int deleteBatchByIds(@Param("ids") Integer[] ids);

    //根据查询条件查询单个数据
    T selTByParams(T t);

    //根据条件查询多条数据
    List<T> selManyByParams(@Param("t") T t);

    //根据条件批量修改数据
    //如果参数是2个以上，那么必须要给参数添加注解，否则Mybatis无法获取参数名称！
    int updBatchTByIds(@Param("ids") Integer[] ids,@Param("t")T t);

    /**
     *    根据条件获取数据条数
     * @param t  查询的条件
     * @return  数据条数
     */
    Long selCountByParams(@Param("t")T t) throws Exception;
}
