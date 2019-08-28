import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification._
import org.apache.spark.ml.evaluation.{MulticlassClassificationEvaluator, RegressionEvaluator}
import org.apache.spark.ml.feature._
import org.apache.spark.ml.regression.{DecisionTreeRegressionModel, DecisionTreeRegressor, GeneralizedLinearRegression, LinearRegression}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}


object MultiClassifier {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("MultiClassifier").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val spark = SparkSession.builder().getOrCreate()
    val path = "C:\\\\Users\\\\dell\\\\Desktop\\\\data.txt"
    val mutiClassifierData = "C:\\Users\\dell\\Desktop\\spark-master\\data\\mllib\\sample_multiclass_classification_data.txt"
    val lrData = "C:\\Users\\dell\\Desktop\\spark-master\\data\\mllib\\sample_linear_regression_data.txt"
    val libSvmData = "C:\\Users\\dell\\Desktop\\spark-master\\data\\mllib\\sample_libsvm_data.txt"
//    runBaseLR(spark,mutiClassifierData)
//    runBaseRest(spark,mutiClassifierData)
//    linalgLr(spark,path)
//    RandomForest(sc,spark,mutiClassifierData)
    val model = PipelineModel.load("C:\\Users\\dell\\Desktop\\hadoop\\randomForestModelForiris\\model")
    val tstData = spark.read.format("libsvm").load(mutiClassifierData)
    val predictions = model.transform(tstData)

    // Select example rows to display.
    predictions.select("predictionLabel", "label", "features").show(5)

    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")




//    generalizedLR(spark,lrData)

//    decisionTree(spark,libSvmData)
//    val model = tst(sc,"C:\\Users\\dell\\Desktop\\hadoop\\randomForestModelForiris\\model")


    spark.stop()


//    val data = sc.textFile("C:\\Users\\dell\\Desktop\\data.txt")

//    //提取特征
//    val  rows = data.map(lines => lines.split(",")).map(s => s.slice(2,s.length))
//    val header = rows.first()
//    val fetures = rows.filter(line => line(0) != header(0)).map(s => s.map(_.toDouble))
//    //提取标签
//    val rows2 = data.map(lines => lines.split(",")).map(s => s(0))
//    val header2 = rows2.first()
//    val label = rows2.filter(s => s != header2).map(s => s.toDouble)
//
//    //组合
//    val processedData = label.zip(fetures)
//    val use_data = processedData.map(line => LabeledPoint(line._1,Vectors.dense(line._2)))
//    //划分测试集和训练集
//    val splits = use_data.randomSplit(Array(0.5,0.5))
//    val train_data = splits(0)
//    val test_data = splits(1)
//    import session.implicits._
//    val train = session.createDataset(train_data)
//    train.show(50)
//    val test = session.createDataset(test_data)




  }

  def runBaseLR(spark:SparkSession,input_data:String):Unit = {
    val data = spark.read.format("libsvm").load(input_data)
    val Array(train,test) = data.randomSplit(Array(1,1))

    val lr = new LogisticRegression().setMaxIter(20).setRegParam(0.3).setElasticNetParam(0.8)

    val model = lr.fit(train)

    val predictions = model.transform(test)

    predictions.show()
    val evaluator = new MulticlassClassificationEvaluator()//.setLabelCol("label").setPredictionCol("prediction")
    val accuracy =evaluator.setMetricName("accuracy").evaluate(predictions);
    val weightedPrecision=evaluator.setMetricName("weightedPrecision").evaluate(predictions);
    val weightedRecall=evaluator.setMetricName("weightedRecall").evaluate(predictions);
    val f1=evaluator.setMetricName("f1").evaluate(predictions);

    println(s"accuracy is $accuracy")
    println(s"weightedPrecision is $weightedPrecision")
    println(s"weightedRecall is $weightedRecall")
    println(s"f1 is $f1")

  }

  def runBaseRest(spark:SparkSession,input_path:String) = {
    val data = spark.read.format("libsvm").load(input_path)
    val Array(train,test) = data.randomSplit(Array(1,1))

    val lr = new LogisticRegression().setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    val model = new OneVsRest()
      .setClassifier(lr)
      .fit(train)

    val predictions = model.transform(test)
    predictions.show(100)

    val evaluator = new MulticlassClassificationEvaluator()

    val accuracy = evaluator.setMetricName("accuracy").evaluate(predictions)
    val weightedPrecision=evaluator.setMetricName("weightedPrecision").evaluate(predictions);
    val weightedRecall=evaluator.setMetricName("weightedRecall").evaluate(predictions);
    val f1=evaluator.setMetricName("f1").evaluate(predictions);


    println(s"Tess error : ${1-accuracy}")
    println(s"weightedPrecision is $weightedPrecision")
    println(s"weightedRecall is $weightedRecall")
    println(s"f1 is $f1")
  }

  def linalgLr(spark:SparkSession,input_path:String) = {
    val training = spark.read.format("libsvm")
      .load(input_path)

    val lr = new LinearRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    // Fit the model
    val lrModel = lr.fit(training)

    // Print the coefficients and intercept for linear regression
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

    // Summarize the model over the training set and print out some metrics
    val trainingSummary = lrModel.summary
    println(s"numIterations: ${trainingSummary.totalIterations}")
    println(s"objectiveHistory: [${trainingSummary.objectiveHistory.mkString(",")}]")
    trainingSummary.residuals.show()
    println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
    println(s"r2: ${trainingSummary.r2}")
  }

  def RandomForest(sc:SparkContext,spark:SparkSession,input_path:String) = {
    val data = spark.read.format("libsvm").load(input_path)

    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)

    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .fit(data)

    val Array(trainingData,testData) = data.randomSplit(Array(0.7,0.3))

    val rf = new RandomForestClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
      .setNumTrees(10)

    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictionLabel")
      .setLabels(labelIndexer.labels)

    val pipline = new Pipeline()
      .setStages(Array(labelIndexer,featureIndexer,rf,labelConverter))

    val model = pipline.fit(data)
//    sc.parallelize(Seq(model),1).saveAsObjectFile("C:\\Users\\dell\\Desktop\\hadoop\\randomForestModelForiris\\model")

    model.save("C:\\Users\\dell\\Desktop\\hadoop\\randomForestModelForiris\\model")
    val predictions = model.transform(testData)

    // Select example rows to display.
    predictions.select("predictionLabel", "label", "features").show(5)

    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")

  }

  def generalizedLR(spark:SparkSession,input_path:String) = {
    val dataset = spark.read.format("libsvm").load(input_path)

    val glr = new GeneralizedLinearRegression()
      .setFamily("gaussian")
      .setLink("identity")
      .setMaxIter(10)
      .setRegParam(0.3)

    val model = glr.fit(dataset)

    // Print the coefficients and intercept for generalized linear regression model
      println(s"Coefficients: ${model.coefficients}")
    println(s"Intercept: ${model.intercept}")

    // Summarize the model over the training set and print out some metrics
    val summary = model.summary
    println(s"Coefficient Standard Errors: ${summary.coefficientStandardErrors.mkString(",")}")
    println(s"T Values: ${summary.tValues.mkString(",")}")
    println(s"P Values: ${summary.pValues.mkString(",")}")
    println(s"Dispersion: ${summary.dispersion}")
    println(s"Null Deviance: ${summary.nullDeviance}")
    println(s"Residual Degree Of Freedom Null: ${summary.residualDegreeOfFreedomNull}")
    println(s"Deviance: ${summary.deviance}")
    println(s"Residual Degree Of Freedom: ${summary.residualDegreeOfFreedom}")
    println(s"AIC: ${summary.aic}")
    println("Deviance Residuals: ")
    summary.residuals().show()
  }

  def decisionTree(spark:SparkSession,input_path:String) = {
    val data = spark.read.format("libsvm").load(input_path)

    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .fit(data)

    val Array(trainingData,testData) = data.randomSplit(Array(0.7,0.3))

    val dt = new DecisionTreeRegressor()
      .setLabelCol("label")
      .setFeaturesCol("indexedFeatures")

    val pipeline = new Pipeline()
      .setStages(Array(featureIndexer,dt))

    val model = pipeline.fit(trainingData)

    val predictions = model.transform(testData)

    predictions.select("prediction", "label", "features").show(5)

    // Select (prediction, true label) and compute test error.
    val evaluator = new RegressionEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("rmse")
    val rmse = evaluator.evaluate(predictions)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse")

    val treeModel = model.stages(1).asInstanceOf[DecisionTreeRegressionModel]
    println(s"Learned regression tree model:\n ${treeModel.toDebugString}")
  }

  def tst(sc:SparkContext,path:String) = {
    sc.objectFile(path).collect()(0)
  }

  def saveModel(sc:SparkContext,model:Any,path:String) = {
    sc.parallelize(Seq(model),1).saveAsObjectFile(path)
  }

  def loadModel(sc:SparkContext,path:String) = {
    sc.objectFile(path)
  }



}
