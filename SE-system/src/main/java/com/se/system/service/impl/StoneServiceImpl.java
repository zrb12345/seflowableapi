package com.se.system.service.impl;

import com.se.system.domain.Stone;
import com.se.system.mapper.StoneMapper;
import com.se.system.service.IStoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author se
 * @date 2024-11-11
 */
@Service
public class StoneServiceImpl implements IStoneService 
{
    @Autowired
    private StoneMapper stoneMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Stone selectStoneById(Integer id)
    {
        return stoneMapper.selectStoneById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param stone 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Stone> selectStoneList(Stone stone)
    {
        return stoneMapper.selectStoneList(stone);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param stone 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertStone(Stone stone)
    {
        return stoneMapper.insertStone(stone);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param stone 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateStone(Stone stone)
    {
        return stoneMapper.updateStone(stone);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStoneByIds(Long[] ids)
    {
        return stoneMapper.deleteStoneByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStoneById(Long id)
    {
        return stoneMapper.deleteStoneById(id);
    }
}
