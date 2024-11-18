//package com.se.system.service.impl;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.ml.Pipeline;
//import org.apache.spark.ml.PipelineModel;
//import org.apache.spark.ml.PipelineStage;
//import org.apache.spark.ml.evaluation.RegressionEvaluator;
//import org.apache.spark.ml.feature.VectorIndexer;
//import org.apache.spark.ml.feature.VectorIndexerModel;
//import org.apache.spark.ml.regression.RandomForestRegressionModel;
//import org.apache.spark.ml.regression.RandomForestRegressor;
//import org.apache.spark.mllib.classification.SVMModel;
//import org.apache.spark.mllib.classification.SVMWithSGD;
//import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
//import org.apache.spark.mllib.regression.LabeledPoint;
//import org.apache.spark.mllib.util.MLUtils;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
//import scala.Tuple2;
//public class ApacheFG{
//    public static void getSpark(){
//        SparkSession spark = SparkSession
//                .builder()
//                .appName("Java Spark SQL basic example")
//                .config("spark.some.config.option", "some-value")
//                .getOrCreate();
//        // Load and parse the data file, converting it to a DataFrame.
//        Dataset<Row> data = spark.read().format("libsvm").load("data/mllib/sample_libsvm_data.txt");
//        // Automatically identify categorical features, and index them.
//        // Set maxCategories so features with > 4 distinct values are treated as continuous.
//        VectorIndexerModel featureIndexer = new VectorIndexer()
//                .setInputCol("features")
//                .setOutputCol("indexedFeatures")
//                .setMaxCategories(4)
//                .fit(data);
//        // Split the data into training and test sets (30% held out for testing)
//        Dataset<Row>[] splits = data.randomSplit(new double[] {0.7, 0.3});
//        Dataset<Row> trainingData = splits[0];
//        Dataset<Row> testData = splits[1];
//        // Train a RandomForest model.
//        RandomForestRegressor rf = new RandomForestRegressor()
//                .setLabelCol("label")
//                .setFeaturesCol("indexedFeatures");
//        // Chain indexer and forest in a Pipeline
//        Pipeline pipeline = new Pipeline()
//                .setStages(new PipelineStage[] {featureIndexer, rf});
//        // Train model. This also runs the indexer.
//        PipelineModel model = pipeline.fit(trainingData);
//        // Make predictions.
//        Dataset<Row> predictions = model.transform(testData);
//        // Select example rows to display.
//        predictions.select("prediction", "label", "features").show(5);
//
//        // Select (prediction, true label) and compute test error
//        RegressionEvaluator evaluator = new RegressionEvaluator()
//                .setLabelCol("label")
//                .setPredictionCol("prediction")
//                .setMetricName("rmse");
//        double rmse = evaluator.evaluate(predictions);
//        System.out.println("Root Mean Squared Error (RMSE) on test data = " + rmse);
//
//        RandomForestRegressionModel rfModel = (RandomForestRegressionModel)(model.stages()[1]);
//        System.out.println("Learned regression forest model:\n" + rfModel.toDebugString());
//
//    }
//    public  static void getGF2(){
//        SparkConf conf = new SparkConf().setAppName("JavaSVMWithSGDExample");
//        SparkContext sc = new SparkContext(conf);
//        // $example on$
//        String path = "data/mllib/sample_libsvm_data.txt";
//        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc, path).toJavaRDD();
//        // Split initial RDD into two... [60% training data, 40% testing data].
//        JavaRDD<LabeledPoint> training = data.sample(false, 0.6, 11L);
//        training.cache();
//        JavaRDD<LabeledPoint> test = data.subtract(training);
//
//        // Run training algorithm to build the model.
//        int numIterations = 100;
//        SVMModel model = SVMWithSGD.train(training.rdd(), numIterations);
//
//        // Clear the default threshold.
//        model.clearThreshold();
//
//        // Compute raw scores on the test set.
//        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map(p ->
//                new Tuple2<>(model.predict(p.features()), p.label()));
//
//        // Get evaluation metrics.
//        BinaryClassificationMetrics metrics =
//                new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels));
//        double auROC = metrics.areaUnderROC();
//
//        System.out.println("Area under ROC = " + auROC);
//
//        // Save and load model
//        model.save(sc, "target/tmp/javaSVMWithSGDModel");
//        SVMModel sameModel = SVMModel.load(sc, "target/tmp/javaSVMWithSGDModel");
//    }
//
//}