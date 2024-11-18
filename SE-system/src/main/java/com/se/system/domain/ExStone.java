package com.se.system.domain;

import com.se.common.annotation.Excel;
import com.se.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 岩石炸药能量利用率对象 ex_stone
 * 
 * @author se
 * @date 2024-11-11
 */
@Data
public class ExStone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 炸药id */
    @Excel(name = "炸药id")
    private Long exId;

    /** 石头id */
    @Excel(name = "石头id")
    private Long stoneId;

    /** 能量利用率 */
    @Excel(name = "能量利用率")
    private Double userPer;

    private String exName;
    private String stoneName;
    /** $column.columnComment */
    private Long id;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("exId", getExId())
            .append("stoneId", getStoneId())
            .append("userPer", getUserPer())
            .append("id", getId())
            .toString();
    }
}
