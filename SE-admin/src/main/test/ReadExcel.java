
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.se.system.domain.Iris;
import org.junit.Test;

public class ReadExcel {
    @Test
    public void test(){
        //要读取的文件名字
        String fileName = "iris.xlsx";

        //使用EasyExcel表格方法读取数据。
        //read方法里三个参数 文件名 模板类 一个解析监听
        //注意：监听里面的方法是需要我们重写的。
        EasyExcel.read(fileName, Iris.class, new AnalysisEventListener<Iris>() {
            int num=0;

            //解析一行运行一次此方法。
            @Override
            public void invoke(Iris student, AnalysisContext analysisContext) {
                num++;
                System.out.println(student.getSepalLength()+" "+student.getSepalWidth()+" "+student.getPetalLength()+" "+student.getPetalWidth()+" "+student.getType());
            }

            //解析所有数据完成，运行此方法。
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读取完成"+"共 "+num+" 条数据");
            }
        }).sheet().doRead();

        /**
         * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
         */
//        public void synchronousRead() {
//            String fileName = "D:\\testExcel.xlsx";
//            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
//            List<DemoData> list = EasyExcel.read(fileName).head(DemoData.class).sheet().doReadSync();
//            for (DemoData data : list) {
//                log.info("读取到数据:{}", new Gson().toJson(data));
//            }
//            // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
//            List<Map<Integer, String>> listMap = EasyExcel.read(fileName).sheet().doReadSync();
//            for (Map<Integer, String> data : listMap) {
//                // 返回每条数据的键值对 表示所在的列 和所在列的值
//                log.info("读取到数据:{}",  new Gson().toJson(data));
//            }
//        }

    }
}

