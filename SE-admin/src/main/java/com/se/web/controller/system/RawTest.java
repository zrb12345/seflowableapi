import com.se.common.annotation.Log;
import com.se.common.core.domain.AjaxResult;
import com.se.common.enums.BusinessType;
import com.se.system.domain.Se;
import com.se.web.controller.system.Student;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.RandomForestRegressor;
import org.apache.spark.sql.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//package com.se.web.controller.system;
//
//import lombok.val;
//import org.apache.spark.ml.classification.RandomForestClassifier;
//import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
//import org.apache.spark.ml.feature.VectorAssembler;
//import org.apache.spark.ml.tuning.ParamGridBuilder;
//import org.apache.spark.ml.tuning.TrainValidationSplit;
//import org.apache.spark.sql.SparkSession;
//
//public class RawTest {
//
//    package com.yyds
//
//
//import org.apache.log4j.{Level, Logger}
//import org.apache.spark.sql.{DataFrame, SparkSession}
//import org.apache.spark.ml.feature.VectorAssembler
//import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
//import org.apache.spark.ml.{Model, Pipeline, PipelineModel, Transformer}
//import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit, TrainValidationSplitModel}
//import org.apache.spark.ml.classification.RandomForestClassifier
//import org.apache.spark.ml.classification.RandomForestClassificationModel
//import scala.util.Random
//
//
//    object ForestModelTest {
//
//        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
//
//        def main(args: Array[String]): Unit = {
//
//
//                // 构建SparkSession实例对象，通过建造者模式创建
//                val spark: SparkSession = {
//                SparkSession
//                        .builder()
//                        .appName(this.getClass.getSimpleName.stripSuffix("$"))
//                        .master("local[1]")
//                        .config("spark.sql.shuffle.partitions", "3")
//                        .getOrCreate()
//        }
//
//
//        // 利用Spark内置的读取CSV数据功能
//        val dataWithHeader: DataFrame = spark.read
//                .option("inferSchema", "true") // 数据类型推断
//                .option("header", "true") // 表头解析
//                .csv("D:\\kaggle\\covtype\\covtype.csv")
//
//
//        // 划分训练集和测试集
//        val Array(trainData, testData) = dataWithHeader.randomSplit(Array(0.9, 0.1))
//        trainData.cache()
//        testData.cache()
//
//        // 输入的特征列
//        val inputCols: Array[String] = trainData.columns.filter(_ != "Cover_Type")
//
//
//        val newAssembler = new VectorAssembler()
//                .setInputCols(inputCols)
//                .setOutputCol("featureVector")
//
//
//        // 随机森林分类器
//        val newClassifier = new RandomForestClassifier()
//                .setSeed(Random.nextLong())
//                .setLabelCol("Cover_Type")
//                .setFeaturesCol("featureVector")
//                .setPredictionCol("prediction")
//
//
//        // 组合为Pipeline
//        val pipeline = new Pipeline().setStages(Array(newAssembler, newClassifier))
//
//        // 使用 SparkML API 内建支持的 ParamGridBuilder 来测试超参数的组合
//        val paramGrid = new ParamGridBuilder() // 4个超参数来说，每个超参数的两个值都要构建和评估一个模型，共计16种超参数组合，会训练出16个模型
//                .addGrid(newClassifier.impurity, Seq("gini", "entropy"))
//                .addGrid(newClassifier.maxDepth, Seq(1, 20))
//                .addGrid(newClassifier.maxBins, Seq(40, 300))
//                .addGrid(newClassifier.numTrees, Seq(10, 20)) // 要构建的决策树的个数
//                .build()
//
//        // 设定评估指标  准确率
//        val multiclassEval = new MulticlassClassificationEvaluator()
//                .setLabelCol("Cover_Type")
//                .setPredictionCol("prediction")
//                .setMetricName("accuracy")
//
//        // 这里也可以用 CrossValidator 执行完整的 k 路交叉验证，但是要额外付出 k 倍的代价，并且在大数据的情况下意义不大。
//        // 所以在这里 TrainValidationSplit 就够用了
//        val validator = new TrainValidationSplit()
//                .setSeed(Random.nextLong())
//                .setEstimator(pipeline)           // 管道
//                .setEvaluator(multiclassEval)     // 评估器
//                .setEstimatorParamMaps(paramGrid) // 超参数组合
//                .setTrainRatio(0.9)               // 训练数据实际上被TrainValidationSplit 划分成90%与10%的两个子集
//
//        val validatorModel: TrainValidationSplitModel = validator.fit(trainData)
//
//        // validator 的结果包含它找到的最优模型。
//        val bestModel = validatorModel.bestModel
//
//        // 打印最优模型参数
//        // 手动从结果 PipelineModel 中提取 DecisionTreeClassificationModel 的实例，然后提取参数
//        println(bestModel.asInstanceOf[PipelineModel].stages.last.extractParamMap)
//
//        // 随机森林分类器有另外一个超参数：要构建的决策树的个数。
//        // 与超参数 maxBins 一样，在某个临界点之前，该值越大应该就能获得越好的效果。然而，代价是构造多棵决策树的时间比建造一棵的时间要长很多倍。
//        val forestModel = bestModel.asInstanceOf[PipelineModel].stages.last.asInstanceOf[RandomForestClassificationModel]
//
//        // 我们对于特征的理解更加准确了
//        println("特征重要性：")
//        println(forestModel.featureImportances.toArray.zip(inputCols).sorted.reverse.foreach(println))
//
//
//        // 这个模型在交叉验证集中达到的准确率是多少？最后，在测试集中能达到什么样的准确率？
//        println("交叉验证集上最大准确率：" + validatorModel.validationMetrics.max)
//        println("测试集上的准确率：" + multiclassEval.evaluate(bestModel.transform(testData)))
//
//
//        // 预测
//        // 得到的“最优模型”实际上是包含所有操作的整个管道，其中包括如何对输入进行转换以适于模型处理，以及用于预测的模型本身。
//        // 它可以接受新的 DataFrame 作为输入。它与我们刚开始时使用的 DataFrame 数据的唯一区别就是缺少“Cover_Type”列
//        bestModel
//                .transform(testData.drop("Cover_Type"))
//                .select("prediction")
//                .show(10)
//
//  }
//
//    }
//
//
//}
// 创建SparkSession
//SparkSession spark = SparkSession.builder().appName("RandomForestRegression").getOrCreate();
//
//// 假设我们有一个数据集df，其中包含特征列（features）和标签列（label）
//Dataset<Row> data = spark.read().format("csv") // 根据实际情况选择合适的数据读取方式
//        .option("header", "true")
//        .option("inferSchema", "true")
//        .load("data.csv"); // 替换为实际文件路径
//
//// 将特征列和标签列合并为一个向量列
//String featuresCol = "features";
//String labelCol = "label";
//VectorAssembler assembler = new VectorAssembler()
//        .setInputCols(Arrays.asList(featuresCol))
//        .setOutputCol("features_vec");
//
//// 构建随机森林回归模型
//RandomForestRegressor rf = new RandomForestRegressor()
//        .setLabelCol(labelCol)
//        .setFeaturesCol("features_vec")
//        .setNumTrees(10); // 设置树的数量，默认是10
//
//// 构建Pipeline来处理整个流程
//Pipeline pipeline = new Pipeline()
//        .setStages(new Stage[] {assembler, rf});
//
//// 拟合模型
//Dataset<Row> modelData = pipeline.fit(data);
//
//// 预测新数据
//Dataset<Row> predictions = modelData.transform(data.sample(False, 0.2)); // 测试集采样
//
//// 输出预测结果
//        predictions.select("prediction", "label").show(); // 显示预测值和真实标签
//
//// 关闭SparkSession
//        spark.stop();
//    }
////模型训练出来后，根据模型生成判断结果
//@Log(title = "模型使用", businessType = BusinessType.IMPORT)
//@PreAuthorize("@ss.hasPermi('system:user:import')")
//@PostMapping("/onlyForecastLabel")
//public AjaxResult onlyForecastLabel(@RequestBody Se Se ) throws IOException {
////       ApacheFG.getGF2();
//    // Load and parse the data file, converting it to a DataFrame.
//    SparkSession spark = SparkSession.builder().appName("RandomForestRegression").master("local").getOrCreate();
//    Dataset<Row> data = spark.read().format("csv")
//            .option("header","true")
//            .option("inferSchema","true")
//            .load("data.csv");
//    String [] features = {"small", "medimu","large","larger"};
//// Automatically identify categorical features, and index them.
//// Set maxCategories so features with > 4 distinct values are treated as continuous.
//    VectorAssembler featureIndexer = new VectorAssembler()
//            .setInputCols(features)
//            .setOutputCol("label1");
//// Split the data into training and test sets (30% held out for testing)
//    Dataset<Row>[] splits = data.randomSplit(new double[] {0.9, 0.1});
//    Dataset<Row> trainingData = splits[0];
////        reflectTransform(spark);
//    List<Student> studentList = new ArrayList<>();
//    Student stu1 = new Student() ;
//    stu1.setSmall(202);
//    stu1.setMedimu(331);
//    stu1.setLarge(336);
//    stu1.setLarger(46);
//    stu1.setLabel(420);
//    Student stu2 = new Student() ;
//    stu2.setSmall(196);
//    stu2.setMedimu(348);
//    stu2.setLarge(326);
//    stu2.setLarger(35);
//    stu2.setLabel(350);
//    Student stu3 = new Student() ;
//    stu3.setSmall(172);
//    stu3.setMedimu(394);
//    stu3.setLarge(218);
//    stu3.setLarger(35);
//    stu3.setLabel(350);
//    studentList.add(stu1);
//    studentList.add(stu2);
//    studentList.add(stu3);
////        Dataset dataFrame = spark.createDataFrame(studentList, Student.class);
////        and these lines to create a typed Dataset:
//    Encoder encoder = Encoders.bean(Student.class);
//    Dataset<Row> dataset = spark.createDataset(studentList, encoder);
//    Dataset<Row> testData = splits[1];
//
//    String modelPath = "C:/"+getUsername()+"/model/label";
//    File file = new File(modelPath);
////        File[] tempList1 = file2.listFiles();
//    if(!file.exists()) {
//        // Train a RandomForest model.
//        RandomForestRegressor rf = new RandomForestRegressor()
//                .setLabelCol("label")
//                .setFeaturesCol("label1")
//                .setNumTrees(10); // 设置树的数量，默认是10;
//// Chain indexer and forest in a Pipeline
//        Pipeline pipeline = new Pipeline()
//                .setStages(new PipelineStage[]{featureIndexer, rf});
//// Train model. This also runs the indexer.
//        PipelineModel model = pipeline.fit(trainingData);
//// Make predictions.
//        Dataset<Row> predictions = model.transform(testData);
//        Dataset<Row> predictions2 = model.transform(dataset);
//        Map<String, String> writeOpts = new HashMap<>();
////        file_name = os.path.basename(item)  # 带了扩展名（.CSV）的文件名
////# 将归一化后的数据存到新的路径下
////                inter_path = os.path.join(root1, cla,file_name)
////        df1.to_csv(inter_path, mode='w', index=False)
//        model.save(modelPath);
//        List<Double> types = predictions.limit(1).select("prediction").as(Encoders.DOUBLE()).collectAsList();
//        return success("预测结果是"+types.get(0));
//    }else{
//        // 模型存储地址
//        PipelineModel pipelineModel = PipelineModel.load(modelPath);// 加载存储的模型
//
//        List<Student> datas = new ArrayList<>();
////        Dataset dataFrame = spark.createDataFrame(studentList, Student.class);
////        and these lines to create a typed Dataset:
//        Encoder encoder2 = Encoders.bean(Student.class);
//        Dataset<Row> dataset2 = spark.createDataset(studentList, encoder);
////            Dataset<Row> predictionDF = pipelineModel.transform(testData);      // 对用户数据进行预测
//        Dataset<Row> predictionDF2 = pipelineModel.transform(dataset2);      // 对用户数据进行预测
//        List<Row> result = predictionDF2.limit(1).collectAsList();
//        Row row = result.get(0);
//        double  label = (double)row.get(0);
////            label large larger medimu small label1 prediction
//        int  large = (int)row.get(1);
//        int  larger = (int)row.get(2);
//        int  medimu = (int)row.get(3);
//        int  small = (int)row.get(4);
//        double  prediction = (double)row.get(6);
//        return success(
//                "块度小: "+small+"\n"+
//                        "块度中: "+medimu+"\n"+
//                        "块度大: "+large+"\n"+
//                        "块度超大: "+larger+"\n"+
//                        "实际粒度是: "+label+"\n"+
//                        "预测结果是: "+prediction);
//    }
//}