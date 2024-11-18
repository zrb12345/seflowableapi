package com.se.system.service.impl;

import com.se.system.domain.Ex;
import com.se.system.mapper.ExMapper;
import com.se.system.service.IExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 炸药Service业务层处理
 * 
 * @author se
 * @date 2024-11-11
 */
@Service
public class ExServiceImpl implements IExService 
{
    @Autowired
    private ExMapper exMapper;

    /**
     * 查询炸药
     * 
     * @param id 炸药主键
     * @return 炸药
     */
    @Override
    public Ex selectExById(Integer id)
    {
        return exMapper.selectExById(id);
    }

    /**
     * 查询炸药列表
     * 
     * @param ex 炸药
     * @return 炸药
     */
    @Override
    public List<Ex> selectExList(Ex ex)
    {
        return exMapper.selectExList(ex);
    }

    /**
     * 新增炸药
     * 
     * @param ex 炸药
     * @return 结果
     */
    @Override
    public int insertEx(Ex ex)
    {
        return exMapper.insertEx(ex);
    }

    /**
     * 修改炸药
     * 
     * @param ex 炸药
     * @return 结果
     */
    @Override
    public int updateEx(Ex ex)
    {
        return exMapper.updateEx(ex);
    }

    /**
     * 批量删除炸药
     * 
     * @param ids 需要删除的炸药主键
     * @return 结果
     */
    @Override
    public int deleteExByIds(Long[] ids)
    {
        return exMapper.deleteExByIds(ids);
    }

    /**
     * 删除炸药信息
     * 
     * @param id 炸药主键
     * @return 结果
     */
    @Override
    public int deleteExById(Long id)
    {
        return exMapper.deleteExById(id);
    }
}
