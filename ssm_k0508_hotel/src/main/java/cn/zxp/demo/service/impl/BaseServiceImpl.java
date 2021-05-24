package cn.zxp.demo.service.impl;

import cn.zxp.demo.dao.BaseMapper;
import cn.zxp.demo.service.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {
        //新建分页的map集合对象
        Map<String,Object> map = new HashMap<String, Object>();
        //开启分页
        PageHelper.startPage(page,limit);
        //进行分页查询  嵌套查询 - 懒加载
        PageInfo<T> pageInfo = new PageInfo<T>(baseMapper.selAll());
        //往map集合中装入相关数据
        map.put("count",pageInfo.getTotal());   //装总的数据条数  key值为："count"  千万不要改
        map.put("data",pageInfo.getList());   //装分页的对象数据   key值为："data"  千万不要改
        return map;
    }

    /**
     *   根据条件分页查询数据
     * @param page  当前页
     * @param limit  每一页的数据条数
     * @param t  查询的条件
     * @return  分页插件的对象数据
     * @throws Exception
     */
    @Override
    public Map<String, Object> findAll(Integer page, Integer limit, T t) {
        //新建分页的map集合对象
        Map<String,Object> map = new HashMap<String, Object>();
        //开启分页
        PageHelper.startPage(page,limit);
        //进行分页查询  嵌套查询 - 懒加载
        PageInfo<T> pageInfo = new PageInfo<T>(baseMapper.selectPageParams(t));
        //往map集合中装入相关数据
        map.put("count",pageInfo.getTotal());   //装总的数据条数  key值为："count"  千万不要改
        map.put("data",pageInfo.getList());   //装分页的对象数据   key值为："data"  千万不要改
        return map;
    }

    @Override
    public List<T> findAll() {
        return baseMapper.selAll();
    }

    @Override
    public String removeByPrimaryKey(Integer id) {
        if (baseMapper.deleteByPrimaryKey(id)>0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public String removeBatchByIds(Integer[] ids) {
        if (baseMapper.deleteBatchByIds(ids)>0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public String saveT(T t) {
        if (baseMapper.insert(t)>0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public String modifyT(T t) {
        if (baseMapper.updateByPrimaryKeySelective(t)>0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public T findTByParams(T t) {
        return baseMapper.selTByParams(t);
    }

    @Override
    public List<T> findManyByParams(T t) {
        return baseMapper.selManyByParams(t);
    }

    @Override
    public String modifyBatchTByIds(Integer[] ids, T t) {
        if(baseMapper.updBatchTByIds(ids, t) > 0){
            return "success";
        }
        return "fail";
    }

    @Override
    public Long findCountByParams(T t) throws Exception {
        return  baseMapper.selCountByParams(t);
    }
}
