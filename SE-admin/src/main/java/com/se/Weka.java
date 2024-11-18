//package com.se;
//
//
//import weka.core.Instances;
//import weka.core.converters.ConverterUtils.DataSource;
//import weka.classifiers.trees.RandomForest;
//import weka.attributeSelection.AttributeSelection;
//import weka.attributeSelection.InfoGainAttributeEval;
//import weka.attributeSelection.Ranker;
//import weka.classifiers.Evaluation;
//
//
//public class RandomForestClassification {
//    public static void main(String[] args) throws Exception {
//        // 加载数据集
//        DataSource source = new DataSource("D:/Weka-3-9-6/data/diabetes.arff");
//        Instances data = source.getDataSet();
//
//        // 设置类索引（假设类标签是最后一个属性）
//        data.setClassIndex(data.numAttributes() - 1);
////        String[] options = new String[]{"-R", "2,4"};  // 保留第2和第4个特征
////        Remove remove = new Remove();
////        remove.setOptions(options);
////        remove.setInputFormat(data);
////        Instances newData = Filter.useFilter(data, remove);
//
//
//        // 创建并配置随机森林分类器
//        RandomForest rf = new RandomForest();
//        rf.setNumIterations(100);  // 设置树的数量
//        rf.setMaxDepth(0);         // 设置树的最大深度，0表示不限深度
//        rf.setNumFeatures(0);      // 自动选择特征数量
//
//
//        // 训练分类器
//        rf.buildClassifier(data);
//
//        // 评估分类器
//        /**
//         * eval: Evaluation类的实例，用于评估模型。
//         * rf: RandomForest类的实例，表示训练的随机森林模型。
//         * data: Instances类的实例，表示加载的数据集。
//         * 10: 交叉验证的折数（folds）。在这里，数据集被分成10个子集（10-fold cross-validation）。
//         * new java.util.Random(1): 随机数生成器，用于确保结果的可重复性。传入相同的种子（这里是1），可以使每次运行的结果一致。
//         */
//        Evaluation eval = new Evaluation(data);
//        eval.crossValidateModel(rf, data, 10, new java.util.Random(1));
//
//        // 输出每棵树的特征选择情况
//        System.out.println("\nFeature selection for each tree:");
//        // 输出评估结果
//        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
//
//        /**
//         * 正确分类实例 (Correctly Classified Instances)
//         * 错误分类实例 (Incorrectly Classified Instances)
//         * Kappa统计量 (Kappa Statistic)
//         * Kappa统计量用于衡量分类的一致性，值范围从-1到1。0表示随机分类，一般认为值大于0.8表示优秀的一致性。
//         * 平均绝对误差 (Mean Absolute Error, MAE)
//         * 平均绝对误差表示预测值与实际值之间的平均绝对差异。在分类问题中，误差值越小表示预测越准确。
//         * 均方根误差 (Root Mean Squared Error, RMSE)
//         * 均方根误差是预测误差的平方的平均值的平方根。与MAE一样，RMSE越小表示预测结果越好。
//         * 绝对误差相对值 (Relative Absolute Error, RAE)
//         * RAE表示模型的平均绝对误差相对于一个简单预测模型（例如，始终预测平均值）的百分比。
//         * 均方根误差相对值 (Root Relative Squared Error, RRSE)
//         * RRSE表示模型的均方根误差相对于基准模型的百分比。
//         * 总实例数 (Total Number of Instances)
//         */
//
//        // 使用AttributeSelection来计算特征重要性
//        AttributeSelection attrSelection = new AttributeSelection();
//        InfoGainAttributeEval evalAttr = new InfoGainAttributeEval();  // 使用信息增益作为特征评价标准
//        Ranker ranker = new Ranker();
//
//        attrSelection.setEvaluator(evalAttr);
//        attrSelection.setSearch(ranker);
//        attrSelection.SelectAttributes(data);
//
//        // 输出特征重要性
//        double[][] rankedAttributes = attrSelection.rankedAttributes();
//        System.out.println("\nFeature Importance:");
//        for (int i = 0; i < rankedAttributes.length; i++) {
//            int attrIndex = (int) rankedAttributes[i][0];
//            double importance = rankedAttributes[i][1];
//            System.out.println(data.attribute(attrIndex).name() + ": " + importance);
//        }
//
//        // 找到最重要的特征
//        int importantFeatureIndex = (int) rankedAttributes[0][0];
//        System.out.println("\nMost important feature: " + data.attribute(importantFeatureIndex).name());
//
//        // 输出分类结果
//        System.out.println("\nClassification results:");
//        for (int i = 0; i < data.numInstances(); i++) {
//            double actualClass = data.instance(i).classValue();
//            String actual = data.classAttribute().value((int) actualClass);
//            double predictedClass = rf.classifyInstance(data.instance(i));
//            String predicted = data.classAttribute().value((int) predictedClass);
//            System.out.println("Instance " + (i + 1) + ": Actual: " + actual + ", Predicted: " + predicted);
//        }
//        // 输出每棵树的Bootstrap样本数量
//        System.out.println("Bootstrap sample size for each tree:");
//        for (int i = 0; i < rf.getNumIterations(); i++) {
//            System.out.println("Tree " + (i + 1) + " has " + data.numInstances() + " samples.");
//        }
//    }
//
//}