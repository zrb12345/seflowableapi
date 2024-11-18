package com.se.system.service;

import com.se.system.domain.Stone;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author se
 * @date 2024-11-11
 */
public interface IStoneService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Stone selectStoneById(Integer id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param stone 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Stone> selectStoneList(Stone stone);

    /**
     * 新增【请填写功能名称】
     * 
     * @param stone 【请填写功能名称】
     * @return 结果
     */
    public int insertStone(Stone stone);

    /**
     * 修改【请填写功能名称】
     * 
     * @param stone 【请填写功能名称】
     * @return 结果
     */
    public int updateStone(Stone stone);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteStoneByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStoneById(Long id);
}
