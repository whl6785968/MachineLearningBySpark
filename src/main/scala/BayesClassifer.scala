import breeze.linalg.DenseVector
import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object BayesClassifer {
  var poi_u = DenseVector(0.0,0.0)
  var neg_u = DenseVector(0.0,0.0)
  var poi_s = DenseVector(0.0,0.0)
  var neg_s = DenseVector(0.0,0.0)
  var p_poi = 0.0
  var p_neg = 0.0


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(s"${this.getClass.getSimpleName}").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val originalData = sc.textFile("C:\\Users\\dell\\Desktop\\data\\waterMelon2.0.txt")

    val train_data = originalData.map(_.split(" ")).map(s => s.slice(1,4)).map(s => s.map(_.toDouble))
//    train_data.collect().foreach(s => s.foreach(println))
    train(train_data)
    println(poi_u)
    println(neg_u)
    println(poi_s)
    println(neg_s)

    val result = predict(Array(0.320,0.370))
    println("result is "+result)


  }

  def train(data:RDD[Array[Double]]) = {
    val array = data.collect()

    val poi_data = data.filter(s => s(2) == 1.0).map(s => DenseVector(s(0),s(1)))
    val neg_data = data.filter(s => s(2) == 0.0).map(s => DenseVector(s(0),s(1)))



    p_poi = (poi_data.count()).toDouble/(data.count()).toDouble
    p_neg =(neg_data.count()).toDouble/(data.count()).toDouble

    val poi_array = poi_data.collect()
    val neg_array = neg_data.collect()


    var poi_sum = DenseVector(0.0, 0.0)
    var neg_sum = DenseVector(0.0, 0.0)

    var s_poi_sum = DenseVector(0.0, 0.0)
    var s_neg_sum = DenseVector(0.0, 0.0)


    for(i<-0 to poi_array.length-1){
      poi_sum = poi_sum :+ poi_array(i)
    }


    for (i<-0 to neg_array.length-1){
      neg_sum = neg_sum :+ neg_array(i)
    }

    poi_u = poi_sum :/ (poi_data.count()).toDouble
    neg_u = neg_sum :/ (neg_data.count()).toDouble

    println(poi_u)
    for(j<-0 to poi_array.length-1){
      val temp = poi_array(j) :- poi_u
      println("temp init:"+temp)
      temp(0) = Math.pow(temp(0),2)
      temp(1) = Math.pow(temp(1),2)
      println("temp calced:"+temp)
      s_poi_sum = s_poi_sum :+ temp
      println("s_poi_sum:"+s_poi_sum)
    }

    poi_s = s_poi_sum :/ (poi_data.count()).toDouble


    for(j<-0 to neg_array.length-1){
      val temp = neg_array(j) :- neg_u
      temp(0) = Math.pow(temp(0),2)
      temp(1) = Math.pow(temp(1),2)
      s_neg_sum = s_neg_sum :+ temp
    }
    neg_s = s_neg_sum :/ (poi_data.count()).toDouble

  }

  def predict(x:Array[Double]):Int = {
    val p1 = calc(x(0),poi_s(0),poi_u(0))
    val p2 = calc(x(1),poi_s(1),poi_u(1))
    val p3 = calc(x(0),neg_s(0),neg_u(0))
    val p4 = calc(x(1),neg_s(1),neg_u(1))

    val r_poi = p_poi * p1 * p2
    val r_neg = p_neg * p3 * p4

    if(r_poi>=r_neg){
      1
    }
    else {
      0
    }
  }

  def calc(x:Double,s:Double,u:Double):Double = {
    val result = (1.0/(Math.sqrt(2.0*Math.PI)*s)) * Math.exp(-(Math.pow((x-u),2))/(2*Math.pow(s,2)))
    result
  }

}
