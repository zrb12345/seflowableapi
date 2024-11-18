package com.se.system.mapper;

import com.se.system.domain.Stone;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author se
 * @date 2024-11-11
 */
public interface StoneMapper 
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
     * @param stone2 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Stone> selectStoneList(Stone stone2);

    /**
     * 新增【请填写功能名称】
     * 
     * @param stone2 【请填写功能名称】
     * @return 结果
     */
    public int insertStone(Stone stone2);

    /**
     * 修改【请填写功能名称】
     * 
     * @param stone2 【请填写功能名称】
     * @return 结果
     */
    public int updateStone(Stone stone2);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStoneById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStoneByIds(Long[] ids);
}
