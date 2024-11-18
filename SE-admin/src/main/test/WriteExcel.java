

import com.alibaba.excel.EasyExcel;
import com.se.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WriteExcel {
    @Test
    public void test(){
        //生成的文件名字
        String fileName = "Student.xlsx";

        //创建一个列表使用列表来装我们要写入的数据。
        List<Student> Students = new ArrayList<>();

        //使用之前写的类实列化出我们的对象
        Student s1 = new Student("1001", "周星星", "男", 122);
        Student s2 = new Student("1002", "吴孟达", "男", 252);
        Student s3 = new Student("1003", "林青霞", "女", 225);
        Student s4 = new Student("1004", "彭于晏", "男", 229);

        //将数据添加进我们的列表中
        Students.add(s1);
        Students.add(s2);
        Students.add(s3);
        Students.add(s4);

        //使用EasyExcel提供的方法写入excel。
        //write方法里的两个参数是 文件名 和 模板类。
        //sheet方法里面对参数是excel表格下面的标签名。
        //doWrite方法的参数是 要写入的数据
        EasyExcel.write(fileName, Student.class).sheet("学生表").doWrite(Students);
    }
}

