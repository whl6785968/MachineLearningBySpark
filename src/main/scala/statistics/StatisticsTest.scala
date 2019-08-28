package statistics

import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.{SparkConf, SparkContext}

object StatisticsTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("statistics").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val data_path = "C:\\Users\\dell\\Desktop\\hadoop\\机器学习BY SPARK\\MLlib机器学习\\数据\\sample_stat.txt"
    val data = sc.textFile(data_path).map(_.split("\t")).map(f => f.map(_.toDouble))
    val data1 = data.map(Vectors.dense(_))
    data1.collect().foreach(println)
    val stat1 = Statistics.colStats(data1)

    println("max is " + stat1.max)
    println("min is " + stat1.min)
    println("mean is " + stat1.mean)
    println("variance is " + stat1.variance)
    println("normL1 is " + stat1.normL1)
    println("normL2 is " + stat1.normL2)


    val corr1 = Statistics.corr(data1,"pearson")
    println(corr1)

    val corr2 = Statistics.corr(data1,"spearman")


    val x1 = sc.parallelize(Array(1.0,2.0,3.0,4.0))
    val y1 = sc.parallelize(Array(5.0,6.0,7.0,8.0))

    val corr3 = Statistics.corr(x1,y1,"pearson")

    val v1 = Vectors.dense(43.0,9.0)
    val v2 = Vectors.dense(44.0,4.0)
    val c1 = Statistics.chiSqTest(v1,v2)

    new LinearRegression()
  }

}
