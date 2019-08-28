import breeze.linalg.DenseVector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control._

object AdaBoostingBySelf {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(s"${this.getClass.getSimpleName}").setMaster("local[4]")

    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val originData = sc.textFile("C:\\Users\\dell\\Desktop\\data\\adaboostingData.txt")
    val testOriginData = sc.textFile("C:\\Users\\dell\\Desktop\\data\\testAdaBoosting.txt")

    val rdd1 = originData.map(t => t.split(" "))
    val leng = rdd1.count().toDouble
    println("initWeight is"+1/leng)

    val initEw = 1/leng

    val initW = new collection.mutable.ArrayBuffer[Double]()
    for(i <- 0 to (leng-1).toInt){
      initW.append(initEw)
    }

    val train_data = rdd1.map(t => LabeledPoint(t(3).toDouble, Vectors.dense(Array(1/leng.toDouble*(t(1).toDouble), 1/leng.toDouble*(t(2).toDouble)))))

//    val rdd2 = testOriginData.map(_.split(" "))
//    val test_data = rdd2.map(t => LabeledPoint(t(3).toDouble, Vectors.dense(Array(t(1).toDouble, t(2).toDouble))))

    //初始化权重


    val modelAndw = new collection.mutable.ArrayBuffer[(DecisionTreeModel,Double)]()
    val stumpAndw = new collection.mutable.ArrayBuffer[(Stump,Double)]()

    val breaks = new Breaks()

    var data = originData.map(_.split(" ")).map(t => t.slice(1, 3)).map(s => s.map(_.toDouble)).map(s => s.map(_*initEw))
    val test_data = originData.map(_.split(" ")).map(s => s.map(_.toDouble))
    val stump_label = originData.map(_.split(" ")).map(t => t(3).toDouble)
    val label_array = new collection.mutable.ArrayBuffer[Double]()
//    stump_label.map(t => {
//      label_array.append(t)
//    })

    for (i <- 0 to stump_label.collect().length-1){
      label_array.append(stump_label.collect()(i))
    }


    breaks.breakable{
      for(i <-0 to 12){
        println(label_array.length)
        data.collect().foreach(t => println("["+t(0)+","+t(1)+"]"))
        //这里应该是根据数据的分布和数据训练模型，问题是如何将分布应用在模型中?
//        val model = DecisionTree.trainClassifier(train_data,2,Map[Int,Int](),"gini",5,32)
//        val predict_label = train_data.map(point => {
//          val prediction = model.predict(point.features)
//          (point.label,prediction)
//        })

        val stump = Stump.buildTree(data)
        val predict_result = data.map(t => {
          val prediction = stump.predict(t)
          prediction.toDouble
        })

        val result_array = new collection.mutable.ArrayBuffer[Double]()
//        predict_result.foreach(t => { result_array.append(t) })

        for(i <-0 to predict_result.collect().length-1){
          result_array.append(predict_result.collect()(i))
        }

        val result_label = new collection.mutable.ArrayBuffer[(Double,Double)]()

        for (h <-0 to result_array.length-1) {
          result_label.append((label_array(h),result_array(h)))
        }

//       val predict_label = predict_result.zip(stump_label)

//        predict_label.collect().foreach(println)
//        val errRate = predict_label.filter(r => r._1 != r._2).count().toDouble/predict_label.count().toDouble

        var equalsRate = 0.0
        for(o <- 0 to result_label.length-1){
          println("result_label " + result_label(o))
          if(result_label(o)._1 != result_label(o)._2){
            equalsRate += 1.0
          }
        }

        println("equalsRate is " + equalsRate)

        val errRate = equalsRate/result_label.length.toDouble

        println("errRate is " + errRate)


        if(errRate>=0.6){
          breaks.break()
        }

        val t_data = train_data.collect()
        val t_data2 = data.collect()

//        for(i <- 0 to t_data.length-1){
//          if(t_data(i).label == model.predict(t_data(i).features)){
//            println("origin initW is " + initW(i))
//            val update_w = initW(i)*(errRate/(1-errRate))
//            println("update_w is " + update_w.toDouble)
//            initW(i) = initW(i)*(errRate/(1-errRate))
//            println("updated initW is" + initW(i))
//          }
//        }

//        val predict_label2 = predict_label.collect()
//
//        for (i <- 0 to predict_label2.length-1){
//          if(predict_label2(i)._1 == predict_label2(i)._2){
//            initW(i) = initW(i)*(errRate/(1-errRate))
//          }
//        }
        for (i <- 0 to initW.length-1){
          println(initW(i))
        }
        for (i <- 0 to result_label.length-1){
          if(result_label(i)._1 == result_label(i)._2){
            initW(i) = initW(i)*(errRate/(1-errRate))
          }
        }

        for (i <- 0 to initW.length-1){
          println(initW(i))
        }
        var sum = 0.0
        for(j <-0 to initW.length-1){
          sum += initW(j)
        }

        println("sum is "+sum)
        for(k <-0 to initW.length-1){
          initW(k) = initW(k)/sum
          println("权重是 " + initW(k))
        }

        val updated_data = new collection.mutable.ArrayBuffer[LabeledPoint]()
        val updated_data2 = new collection.mutable.ArrayBuffer[Array[Double]]()

//       for(m <- 0 to t_data.length-1){
//         val array = t_data(m).features.toDense.values
//         val narray = Array(array(0)*initW(m),array(1)*initW(m))
//         print("[" + narray(0) +","+ narray(1) + "]")
//
//         for (n <- 0 to array.length-1){
//           array(n) = array(n)*initW(m)
//         }
//
//         updated_data.append(LabeledPoint(t_data(m).label,Vectors.dense(narray)))
//       }
        for(m <-0 to t_data2.length-1){
//          val array = Array(t_data2(i)(1),t_data2(i)(2))
//          println("[" + t_data2(i)(0)+"," + t_data2(i)(1) + "]")
          val narray = Array(t_data2(m)(0)*initW(m),t_data2(m)(1)*initW(m))
          updated_data2.append(Array(narray(0),narray(1)))
        }

        val u_d = sc.parallelize(updated_data2).repartition(2)

        data = u_d
//        train_data = u_d

        //模型权重
        val w = 0.5*Math.log((1-errRate)/Math.max(errRate,Math.exp(-15)))
        println("w is" + w)

//        modelAndw.append((model,w))
        stumpAndw.append((stump,w))
        println("================================")
      }
    }

//    for(i <- 0 to modelAndw.length-1){
//      println(modelAndw(i))
//    }
  }

}
