package com.se.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.se.common.annotation.Excel;
import com.se.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 stone2
 * 
 * @author se
 * @date 2024-11-11
 */
@Data
public class Stone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 所需总能量 */
    @Excel(name = "所需总能量")
    private Double energy;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("energy", getEnergy())
            .toString();
    }
}
