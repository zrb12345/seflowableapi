package com.se.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.se.common.annotation.Excel;
import lombok.Data;
@Data
public class Se extends BaseDomain {
    /** 参数主键 */
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;

    //块度
    @ExcelProperty("kd")
    private Double kd;

    //密度
    @ExcelProperty("md")
    private Double md;

    //强度
    @ExcelProperty("qd")
    private Double qd;

    //泊松比
    @ExcelProperty("bsb")
    private Double bsb;


    //弹性模量
    @ExcelProperty("txml")
    private Double txml;

    //大块率
    @ExcelProperty("dkl")
    private Double dkl;

    //炸药单耗
    @ExcelProperty("zydh")
    private Double zydh;

    //孔径
    @ExcelProperty("kjin")
    private Double kjin;

    //孔距
    @ExcelProperty("kju")
    private Double kju;

    //炸药类型
    @ExcelProperty("type")
    private Integer type;


    private String createBy;
    private String status;


    public Se(){

    }
}




