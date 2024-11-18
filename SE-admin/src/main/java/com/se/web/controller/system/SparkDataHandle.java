package com.se.web.controller.system;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.Serializable;
import java.util.Iterator;

public class SparkDataHandle implements Serializable {

        public static String  handler(Dataset<Row> rowDataset) {

            JavaRDD<Row> dataRDD = rowDataset.toJavaRDD();
            final String[] res = {""};
            dataRDD.foreachPartition(new VoidFunction<Iterator<Row>>() {
                @Override
                public void call(Iterator<Row> rowIterator) throws Exception {
                    while (rowIterator.hasNext()) {
                        Row next = rowIterator.next();
                        Integer  small = (Integer) next.get(0);
                           res[0] +=small;
                    }
                }
            });

            // 转换为 lambda 表达式
//            dataRDD.foreachPartition((VoidFunction<Iterator<Row>>) rowIterator -> {
//                while (rowIterator.hasNext()) {
//                    Row next = rowIterator.next();
//                    String id = next.getAs("id");
//                    if (id.equals("123")) {
//                        String startTime = next.getAs("time");
//                        // 其他业务逻辑
//                    }
//                }
//            });
            String resstr = res[0];
            return resstr;
        }

}

//String modelPath = "C:/"+getUsername()+"/model";
////       ApacheFG.getGF2();
//// Load and parse the data file, converting it to a DataFrame.
//SparkSession spark = SparkSession.builder().appName("RandomForestRegression").master("local").getOrCreate();
//Dataset<Row> data = spark.read().format("csv")
//        .option("header","true")
//        .option("inferSchema","true")
//        .load("data.csv");
//String [] features = {"small", "medimu","large","larger"};
//// Automatically identify categorical features, and index them.
//// Set maxCategories so features with > 4 distinct values are treated as continuous.
//VectorAssembler featureIndexer = new VectorAssembler()
//        .setInputCols(features)
//        .setOutputCol("label1");
//// Split the data into training and test sets (30% held out for testing)
//Dataset<Row>[] splits = data.randomSplit(new double[] {0.99, 0.01});
//Dataset<Row> trainingData = splits[0];
//Dataset<Row> testData = splits[1];
//// Train a RandomForest model.
//RandomForestRegressor rf = new RandomForestRegressor()
//        .setLabelCol("label")
//        .setFeaturesCol("label1")
//        .setNumTrees(10); // 设置树的数量，默认是10;
//// Chain indexer and forest in a Pipeline
//Pipeline pipeline = new  Pipeline()
//        .setStages(new PipelineStage[] {featureIndexer, rf});
//// Train model. This also runs the indexer.
//PipelineModel model = pipeline.fit(trainingData);
//// Make predictions.
//Dataset<Row> predictions = model.transform(testData);
//Map<String, String> writeOpts = new HashMap<>();
////        file_name = os.path.basename(item)  # 带了扩展名（.CSV）的文件名
////# 将归一化后的数据存到新的路径下
////                inter_path = os.path.join(root1, cla,file_name)
////        df1.to_csv(inter_path, mode='w', index=False)
////        String dirStr = "C:"+ getUsername() ;
////        File directory = new File(dirStr);
////mkdir
////        boolean hasSucceeded = directory.mkdir();
////        System.out.println("创建文件夹结果（不含父文件夹）" + hasSucceeded);
//        predictions.withColumn("small", col("small").cast("String"))
//        .withColumn("medimu", col("medimu").cast("String"))
//        .withColumn("large", col("large").cast("String"))
//        .withColumn("larger", col("larger").cast("String"))
//        .withColumn("label1", col("label1").cast("String"))
//        .withColumn("prediction", col("prediction").cast("String"))
//        .write().mode(SaveMode.Overwrite)
//                .option("header",true)
//                .options(writeOpts).csv( "C:/"+ getUsername());
//
//        predictions.select("prediction", "label", "label1").show( );
//double rmse = 0 ;
//        try{
//RegressionEvaluator evaluator = new RegressionEvaluator()
//        .setLabelCol("label")
//        .setPredictionCol("prediction")
//        .setMetricName("rmse");
//rmse = evaluator.evaluate(predictions);
//            System.out.println("Root Mean Squared Error (RMSE) on test data = " + rmse);
//        }catch (Exception e){
//        System.out.println(e);
//        }
//RandomForestRegressionModel rfModel = (RandomForestRegressionModel)(model.stages()[1]);
//        System.out.println("Learned regression forest model:\n" + rfModel.toDebugString());
//        predictions.write().mode(SaveMode.Append).parquet(modelPath);
//// Select example rows to display.
//final String[] str =new String[5];
//JavaRDD<Row> dataRDD = predictions.toJavaRDD();
//StringBuilder strll = new StringBuilder();
//        return success(strll);
//            predictions.withColumn("small", col("small").cast("String"))
//                    .withColumn("medimu", col("medimu").cast("String"))
//                    .withColumn("large", col("large").cast("String"))
//                    .withColumn("larger", col("larger").cast("String"))
//                    .withColumn("label1", col("label1").cast("String"))
//                    .withColumn("prediction", col("prediction").cast("String"))
//                    .write().mode(SaveMode.Overwrite)
//                    .option("header",true)
//                    .options(writeOpts).csv("C:/"+ getUsername());
//
//            predictions.select("prediction", "label", "label1").show( );
//            try{
//                RegressionEvaluator evaluator = new RegressionEvaluator()
//                        .setLabelCol("label")
//                        .setPredictionCol("prediction")
//                        .setMetricName("rmse");
//                double rmse = evaluator.evaluate(predictions);
//                System.out.println("Root Mean Squared Error (RMSE) on test data = " + rmse);
//            }catch (Exception e){
//                System.out.println(e);
//            }
//            RandomForestRegressionModel rfModel = (RandomForestRegressionModel)(model.stages()[1]);
//            System.out.println("Learned regression forest model:\n" + rfModel.toDebugString());
//            predictions.write().mode(SaveMode.Append).parquet(modelPath);
//        model.write.overwrite().save("/tmp/unfit-lr-model")
//        }else{
//            // 模型存储地址
//            PipelineModel pipelineModel = PipelineModel.load(modelPath);// 加载存储的模型
//            Dataset predictionDF = pipelineModel.transform(testData);      // 对用户数据进行预测
//        }
////        Select example rows to display.
////        final String[] str =new String[5];
////        JavaRDD<Row> dataRDD = predictions.toJavaRDD();
////        StringBuilder strll = new StringBuilder();
////        return success(strll);
//        //读取C盘的用户文件夹下的csv文件然后解析成对象列表转为excel在返回
//        File fileres = new File("C:/"+getUsername());
//        File[] tempList = fileres.listFiles();
//        String  fileName = "";
//        for (int i = 0; i < tempList.length; i++) {
//            if(tempList[i].isFile()){
//                String path = tempList[i].getPath();
//                String type = path.substring(path.length()-3,path.length());
//                if(type.equals("csv"))
//                {
//                    fileName = tempList[i].getName();
//                    break;
//                }
//            }
//        }
//        File filepre = new File("C:/"+getUsername()+"/"+fileName);
//
//        MultipartFile cMultiFile = new MockMultipartFile("file", filepre.getName(), null, new FileInputStream(filepre));
//
//
//        List<SeExcel> list = EasyExcel.read(cMultiFile.getInputStream()).head(Se.class).sheet().doReadSync();
////        SeQueryCriteria queryCriteria = new SeQueryCriteria();
////        queryCriteria.setIsDel(0);
////        List<Se> list = seService.selectSeList(queryCriteria);
//        ExcelUtil<Se> util = new ExcelUtil<Se>(Se.class);
//        List<Se> ListTrue = generateFromExcel(list);
//        util.exportExcel(response, ListTrue, "预测数据");

//        File file2 = new File("C:/"+getUsername()+"/"+fileName);
//        if (file2.exists()) {
//            response.setContentType("application/force-download");// Set force download not to open
//            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// set filename
//            response.setCharacterEncoding("UTF-8");
//            byte[] uft8bom = {(byte) 0xef, (byte) 0xbb, (byte) 0xbf};
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null;
//            BufferedInputStream bis = null;
//            try {
//                fis = new FileInputStream(file2);
//                bis = new BufferedInputStream(fis);
//                OutputStream os = response.getOutputStream();
//                os.write(uft8bom);
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    os.write(buffer, 0, i);
//                    i = bis.read(buffer);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (fis != null) {
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        if (file2.delete()) {
//            logger.info(file2.getName() + " file has been deleted！");
//        } else {
//            logger.info("File deletion failed！");
//        }

//    private List<Se> generateFromExcel(List<SeExcel> list) {
//        List<Se> res = new ArrayList<>();
//        for (int i = 0; i < list.size() ; i++) {
//            SeExcel item = list.get(i);
//            Se se = new Se();
//            se.setType(Integer.valueOf(item.getPrediction()));
//            se.setAverageBlockSize(Integer.valueOf(item.getAverageBlockSize()));
//            se.setBoulderYield(Integer.valueOf(item.getBoulderYield()));
//            se.setOverexcavationThickness(Integer.valueOf(item.getOverexcavationThickness()));
////            se.set(Integer.valueOf(item.getType()));
//
//        }
//        return  res;
//    }