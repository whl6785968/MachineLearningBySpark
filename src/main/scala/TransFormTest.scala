import org.apache.spark.{SparkConf, SparkContext}

object TransFormTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(s"${this.getClass.getSimpleName}").setMaster("local[4]")
    val sc = new SparkContext(conf)

    val data = Array((1,1.0),(1,2.0),(1,3.0),(2,4.0),(2,5.0),(2,6.0))

    val rdd = sc.parallelize(data,2)

    val combine1 = rdd.combineByKey(createCombiner = (v:Double) => (v:Double,1),
      mergeValue = (c:(Double,Int),v:Double) => (c._1 + v,c._2 + 1),
      mergeCombiners = (c1:(Double,Int),c2:(Double,Int)) => (c1._1+c2._1,c1._2+c2._2),
      numPartitions = 2
    )

    combine1.collect().foreach(println)

    val treeAgg = rdd.treeAggregate((0,0.0))(seqOp = ((u,t) => (u._1+t._1,u._2+t._2)),
      combOp = (u1,u2) => (u1._1+u2._1,u1._2+u2._2),
      depth = 2
    )

    println(treeAgg)
  }

}
