package com.se.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
@Data
public class SeExcel   {

//            .withColumn("medimu", col("medimu").cast("String"))
//                .withColumn("large", col("large").cast("String"))
//                .withColumn("larger", col("larger").cast("String"))
//                .withColumn("label1", col("label1").cast("String"))
//                .withColumn("prediction", col("prediction").cast("String"))

    /** 参数主键 */
    @ExcelProperty("small")
    //平均块度
    private String averageBlockSize;
    //    @ExcelProperty("by")
    @ExcelProperty("medimu")
    //大块率
    private String boulderYield;
    //    @ExcelProperty("sc")
    @ExcelProperty("large")
    //炸药单耗
    private String specificCharge;
    //    @ExcelProperty("ot")
    @ExcelProperty("larger")
    //超挖厚度
    private String overexcavationThickness;
    //    @ExcelProperty("Spe")
    @ExcelProperty("label1")
    private String type;

    @ExcelProperty("prediction")
    private String prediction;


}





    
