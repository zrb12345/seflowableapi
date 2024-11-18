package com.se.system.mapper;

import com.se.system.domain.ExStone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 岩石炸药能量利用率Mapper接口
 * 
 * @author se
 * @date 2024-11-11
 */
public interface ExStoneMapper 
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
     * 删除岩石炸药能量利用率
     * 
     * @param id 岩石炸药能量利用率主键
     * @return 结果
     */
    public int deleteExStoneById(Long id);

    /**
     * 批量删除岩石炸药能量利用率
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExStoneByIds(Long[] ids);

    @Select("select * from ex_stone where stone_id = #{type} and ex_id = #{exId}")
    ExStone selectExStoneByStoneId(@Param("type") Integer type,@Param("exId") Integer exId);
}
