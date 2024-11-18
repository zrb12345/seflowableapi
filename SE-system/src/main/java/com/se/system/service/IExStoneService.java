package com.se.system.service;

import com.se.system.domain.ExStone;

import java.util.List;

/**
 * 岩石炸药能量利用率Service接口
 * 
 * @author se
 * @date 2024-11-11
 */
public interface IExStoneService 
{
    /**
     * 查询岩石炸药能量利用率
     * 
     * @param id 岩石炸药能量利用率主键
     * @return 岩石炸药能量利用率
     */
    public ExStone selectExStoneById(Long id);

    /**
     * 查询岩石炸药能量利用率列表
     * 
     * @param exStone 岩石炸药能量利用率
     * @return 岩石炸药能量利用率集合
     */
    public List<ExStone> selectExStoneList(ExStone exStone);

    /**
     * 新增岩石炸药能量利用率
     * 
     * @param exStone 岩石炸药能量利用率
     * @return 结果
     */
    public int insertExStone(ExStone exStone);

    /**
     * 修改岩石炸药能量利用率
     * 
     * @param exStone 岩石炸药能量利用率
     * @return 结果
     */
    public int updateExStone(ExStone exStone);

    /**
     * 批量删除岩石炸药能量利用率
     * 
     * @param ids 需要删除的岩石炸药能量利用率主键集合
     * @return 结果
     */
    public int deleteExStoneByIds(Long[] ids);

    /**
     * 删除岩石炸药能量利用率信息
     * 
     * @param id 岩石炸药能量利用率主键
     * @return 结果
     */
    public int deleteExStoneById(Long id);

    String getConsumeOfTop5(List<Integer> params);
}
