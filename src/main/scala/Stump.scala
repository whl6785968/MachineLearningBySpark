import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.util.Random

object Stump {
  def buildTree(data:RDD[Array[Double]]) = {

    val rows = data.count().toInt
    val features = 2
    val poi = data.filter(s => s(3) == 1).count().toDouble
    val neg = data.filter(s => s(3) == 0).count().toDouble
    val gain_array1 = new collection.mutable.ArrayBuffer[(Double,Double)]()
    val gain_array2 = new collection.mutable.ArrayBuffer[(Double,Double)]()
    val ent = -((poi/rows.toDouble)*(Math.log(poi/rows)/Math.log(2.0))+(neg/rows.toDouble)*(Math.log(neg/rows)/Math.log(2.0)))
    println("ent is " + ent)

    val best_candidate = new collection.mutable.ArrayBuffer[(Double,Double)]()
    var best_split = 0.0
    var curr_feature = 0
    for (i <- 0 to features-1){
      val candidate_attr = new collection.mutable.ArrayBuffer[Double]()
      val gain_array = new collection.mutable.ArrayBuffer[(Double,Double)]()
      val max = new collection.mutable.ArrayBuffer[Double]()
      //[index,feature1,feature2,label]
      //[feature_x,label]
      val attr = data.map(s => (s(i+1),s(3))).sortBy(s => s._1)
      val attr_value = attr.collect()
      //[(feature_x,label)]
      for(j <- 0 to attr_value.length-2) {
        val curr_value = attr_value(j)._1
        val next_value = attr_value(j + 1)._1
        val candidate_value = (curr_value + next_value) / 2
        candidate_attr.append(candidate_value)
      }
      for(k <-0 to candidate_attr.length-1){
        val curr_candidate = candidate_attr(k)
//        println("当前候选者为" + curr_candidate)
        val left_data = attr_value.filter(s => s._1<curr_candidate)
        val right_data = attr_value.filter(s => s._1>curr_candidate)

        val l_poi = left_data.filter(s => s._2 == 1.0).length.toDouble
        val l_neg = left_data.filter(s => s._2 == 0.0).length.toDouble
        val l_count = left_data.length.toDouble

        val r_poi = right_data.filter(s => s._2 == 1.0).length.toDouble
        val r_neg = right_data.filter(s => s._2 == 0.0).length.toDouble
        val r_count = right_data.length.toDouble

        val l_ent = -(((l_poi/l_count)*(Math.log(l_poi/l_count)/Math.log(2.0)))+((l_neg/l_count)*(Math.log(l_neg/l_count)/Math.log(2.0))))
        val r_ent = -(((r_poi/r_count)*(Math.log(r_poi/r_count)/Math.log(2.0)))+((r_neg/r_count)*(Math.log(r_neg/r_count)/Math.log(2.0))))

        val gain = (curr_candidate,(ent-(l_ent*(l_count/rows.toDouble)) + (r_ent*(r_count/rows.toDouble))))
//        if(i == 0){
//          gain_array1.append(gain)
//        }else{
//          gain_array2.append(gain)
//        }
        gain_array.append(gain)

        max.append(0.0)
        max.append(0.0)
        for (m <- 0 to gain_array.length-1){
          if(gain_array(m)._2>max(1)){
            max(0) = gain_array(m)._1
            max(1) = gain_array(m)._2
          }
        }
      }
      best_candidate.append((max(0),max(1)))
    }


    println(best_candidate.length)
    var max = 0.0
    for (n <- 0 to best_candidate.length-1){
      if(best_candidate(n)._2>best_split){
        max = best_candidate(n)._2
        best_split = best_candidate(n)._1
        curr_feature = n
      }
    }
    println("the best split value is" + best_split + " in attr"+curr_feature )

//    val randomFeatures = Random.nextInt(features)
//    val colValue = data.map(_.apply(randomFeatures))
//    val randomRows = Random.nextInt(rows)
//    val colMax = colValue.max()
//    val colMin = colValue.min()
//    val splitValue = colMin + (colMax-colMin) * Random.nextDouble()

    val dataLeft = data.filter(s => s(features)<best_split)
    val dataRight = data.filter(s => s(features)>best_split)

    new Stump(best_split,curr_feature)

  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(s"${this.getClass.getSimpleName}").setMaster("local[4]")

    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val originData = sc.textFile("C:\\Users\\dell\\Desktop\\data\\adaboostingData.txt")
//    val data = originData.map(_.split(" ")).map(t => t.slice(1,3)).map(s => s.map(_.toDouble))
//    val test_data = originData.map(_.split(" ")).map(s => s.map(_.toDouble))

//    val stump = buildTree(data)
    val data = originData.map(_.split(" ")).map(s => s.map(_.toDouble))
    val stump = buildTree(data)
    val result = data.map(t => {
      val prediction = stump.predict(Array(t(1),t(2)))
      (prediction.toDouble,t(3))
    })

    val errRate = result.filter(t => t._1 != t._2).count().toDouble/data.count().toDouble
    result.collect().foreach(println)
    println("errRate is "+errRate)




  }
}

class Stump(splitValue:Double,features:Int) extends Serializable {
  def predict(x:Array[Double]):Int = {
    if(x(features)<splitValue){
      0
    }
    else {
      1
    }
  }
}


