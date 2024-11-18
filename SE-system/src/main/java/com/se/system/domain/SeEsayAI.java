package com.se.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.se.common.annotation.Excel;
import lombok.Data;

@Data
public class SeEsayAI   {
    /** 参数主键 */
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;
    //块度
    @ExcelProperty("kd")
    private Integer kd;
    //密度
    @ExcelProperty("md")
    private Integer md;
    //强度
    @ExcelProperty("qd")
    private Integer qd;
    //泊松比
    @ExcelProperty("bsb")
    private Integer bsb;
    //弹性模量
    @ExcelProperty("txml")
    private Integer txml;
    //大块率
    @ExcelProperty("dkl")
    private Integer dkl;
    //炸药单耗
    @ExcelProperty("zydh")
    private Integer zydh;
    //孔径
    @ExcelProperty("kjin")
    private Integer kjin;
    //孔距
    @ExcelProperty("kju")
    private Integer kju;
    //炸药类型
    @ExcelProperty("type")
    private Integer type;
    
    public SeEsayAI (Se se){
        this.md=se.getMd().intValue();
        this.qd=se.getQd().intValue();
        this.kd=se.getKd().intValue();
        this.dkl=se.getDkl().intValue();
        this.bsb=se.getBsb().intValue();
        this.txml=se.getTxml().intValue();
        this.kjin=se.getKjin().intValue();
        this.kju=se.getKju().intValue();
        this.zydh=se.getZydh().intValue();
        this.id=se.getId();
        this.type=se.getType();
    }
    
}




