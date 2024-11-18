package com.se.web.controller.system;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;

public class TxtToParquetDemo {

    public static void main(String[] args) {
//        把java对象转化为DateSet
//        List<com.se.Student> studentList = new ArrayList<>();
////        Dataset dataFrame = spark.createDataFrame(studentList, Student.class);
////        and these lines to create a typed Dataset:
//        Encoder encoder = Encoders.bean(Student.class);
//
//        Dataset dataset = spark.createDataset(studentList, encoder);

        SparkConf conf = new SparkConf().setAppName("TxtToParquet").setMaster("local");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        reflectTransform(spark);//Java反射
        dynamicTransform(spark);//动态转换
    }
    /**
     * 通过Java反射转换
     * @param spark
     */
    private static void reflectTransform(SparkSession spark)
    {
        JavaRDD<String> source = spark.read().textFile("stuInfo.txt").javaRDD();

        JavaRDD<Student> rowRDD = source.map(line -> {
            String parts[] = line.split(",");

            Student stu = new Student();
//            stu.setSid(parts[0]);
//            stu.setSname(parts[1]);
//            stu.setSage(Integer.valueOf(parts[2]));
            return stu;
        });

        Dataset<Row> df = spark.createDataFrame(rowRDD, Student.class);
        df.select("sid", "sname", "sage").
                coalesce(1).write().mode(SaveMode.Append).parquet("parquet.res");
    }

    /**
     * 动态转换
     * @param spark
     */
    private static void dynamicTransform(SparkSession spark)
    {
        JavaRDD<String> source = spark.read().textFile("stuInfo.txt").javaRDD();

        JavaRDD<Row> rowRDD = source.map( line -> {
            String[] parts = line.split(",");
            String sid = parts[0];
            String sname = parts[1];
            int sage = Integer.parseInt(parts[2]);

            return RowFactory.create(
                    sid,
                    sname,
                    sage
            );
        });

        ArrayList<StructField> fields = new ArrayList<StructField>();
        StructField field = null;
        field = DataTypes.createStructField("sid", DataTypes.StringType, true);
        fields.add(field);
        field = DataTypes.createStructField("sname", DataTypes.StringType, true);
        fields.add(field);
        field = DataTypes.createStructField("sage", DataTypes.IntegerType, true);
        fields.add(field);

        StructType schema = DataTypes.createStructType(fields);

        Dataset<Row> df = spark.createDataFrame(rowRDD, schema);
        df.coalesce(1).write().mode(SaveMode.Append).parquet("parquet.res1");


    }

}