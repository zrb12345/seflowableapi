package com.se.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.se.common.annotation.Excel;
import com.se.common.core.domain.BaseEntity;

/**
 * 炸药对象 ex
 * 
 * @author se
 * @date 2024-11-11
 */
@Data
public class Ex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 炸药名称 */
    @Excel(name = "炸药名称")
    private String name;

    /** 最大做功能力 */
    @Excel(name = "最大做功能力")
    private Double power;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("power", getPower())
            .toString();
    }
}
