import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control.Breaks

object AdaBoostingBySelf2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(s"${this.getClass.getSimpleName}").setMaster("local[4]")

    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")


    val originData = sc.textFile("C:\\Users\\dell\\Desktop\\data\\adaboostingData.txt")
    val rdd1 = originData.map(t => t.split(" "))
    val leng = rdd1.count().toDouble
    println("initWeight is"+1/leng)
    val initEw = 1/leng.toDouble
    val init_w = new collection.mutable.ArrayBuffer[Double]()
    for(i <- 0 to leng.toInt)
    {
        init_w.append(initEw)
    }

    val data = originData.map(_.split(" ")).map(s => s.map(_.toDouble))
    val t_data = data.map(s => Array(s(0),s(1)*initEw,s(2)*initEw,s(3)))

    val breaks = new Breaks()

    breaks.breakable{
      for(i <- 0 to 12){
        val stump = Stump.buildTree(data.map(t => Array(t(1),t(2))))

        val prediciton_label = t_data.map(t => {
          val prediction = stump.predict(Array(t(1),t(2)))
          (prediction.toDouble,t(3))
        })

        prediciton_label.collect().foreach(println)

        val errRate = prediciton_label.filter(r => r._1 != r._2).count().toDouble/prediciton_label.count().toDouble

        if(errRate>0.6 || errRate ==0 ){
          breaks.break()
        }

        prediciton_label.map(s => {
          if(s._1 == s._2){

          }
        })
      }
    }
  }

}
