package com.se;
import com.alibaba.excel.annotation.ExcelProperty;
//public class WriteExcel {
//    @Test
//    public void test(){
//        //生成的文件名字
//        String fileName = "iris.csv";
//
//        //创建一个列表使用列表来装我们要写入的数据。
//        List<Iris> irises = new ArrayList<>();
//
//        //使用之前写的类实列化出我们的对象
//        Iris s1 = new Iris(1.1f, 1.2f, 1, 122);
//        Iris s2 = new Iris(2.0f, 2, 2, 252);
//
//        //将数据添加进我们的列表中
//        irises.add(s1);
//        irises.add(s2);
//
//        //使用EasyExcel提供的方法写入excel。
//        //write方法里的两个参数是 文件名 和 模板类。
//        //sheet方法里面对参数是excel表格下面的标签名。
//        //doWrite方法的参数是 要写入的数据
//        EasyExcel.write(fileName, Iris.class).sheet().doWrite(irises);
//    }

//}
public class Student {
    //@ExcelProperty 是easyexcel给我们提供的一个方法。
//@ExcelProperty 相当于我们表格里面的字段名，如果这里不写这里就是我们的属性名"id"
    @ExcelProperty("学号")
    private String id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("年龄")
    private int age;

    public Student(){

    }
    public Student(String id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
