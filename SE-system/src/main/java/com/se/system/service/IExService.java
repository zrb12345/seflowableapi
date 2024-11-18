package com.se.system.service;

import com.se.system.domain.Ex;

import java.util.List;

/**
 * 炸药Service接口
 * 
 * @author se
 * @date 2024-11-11
 */
public interface IExService 
{
    /**
     * 查询炸药
     * 
     * @param id 炸药主键
     * @return 炸药
     */
    public Ex selectExById(Integer id);

    /**
     * 查询炸药列表
     * 
     * @param ex 炸药
     * @return 炸药集合
     */
    public List<Ex> selectExList(Ex ex);

    /**
     * 新增炸药
     * 
     * @param ex 炸药
     * @return 结果
     */
    public int insertEx(Ex ex);

    /**
     * 修改炸药
     * 
     * @param ex 炸药
     * @return 结果
     */
    public int updateEx(Ex ex);

    /**
     * 批量删除炸药
     * 
     * @param ids 需要删除的炸药主键集合
     * @return 结果
     */
    public int deleteExByIds(Long[] ids);

    /**
     * 删除炸药信息
     * 
     * @param id 炸药主键
     * @return 结果
     */
    public int deleteExById(Long id);
}
