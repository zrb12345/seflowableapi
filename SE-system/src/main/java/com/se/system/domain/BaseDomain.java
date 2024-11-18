package com.se.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * @ClassName 公共模型
 * @Author wyr<2910502607@qq.com>
 * @Date 2020/6/13
 **/
@Getter
@Setter
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField(fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @TableField(fill= FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    @JsonIgnore
    private Integer isDel;

    @JsonIgnore
    private String remark;
    @JsonIgnore
    private String updateBy;
    @JsonIgnore
    private String createBy;
}
