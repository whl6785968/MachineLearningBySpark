import breeze.linalg.{DenseMatrix, DenseVector, det, inv}
import org.apache.spark.{SparkConf, SparkContext}


object SomeTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName(s"${this.getClass.getSimpleName}")

    val sc = new SparkContext(conf)

    sc.setLogLevel("ERROR")

//    }
//    val entP = Math.log(8.0/13.0)/Math.log(2.0)
//    val entN = Math.log(5.0/13.0)/Math.log(2.0)
//
//    val ent = -((8.0/13.0)*entP + (5.0/13.0)*entN)
//
//    println("ent is " + ent)
//    val gain = 0.998 - (13.0/17.0)*ent
//    println("gain is " + gain)

    val matrix1 = DenseMatrix((1.0,3.0),(2.0,4.0))
    val inv1 = inv(matrix1)
    println(matrix1)
    println(inv1)
    println(det(matrix1))

    val a = DenseVector(1,2)
//    val b = a.t


    val c = a.t * a
    println(c)

    val a1 = a.t
    val size = a.size
    println(size)
    val array = new collection.mutable.ArrayBuffer[Double]()

    for (i <- 0 to size-1){
      var sum = 0.0
      for(j <- 0 to matrix1.cols-1){
        sum += a(j)*matrix1(j,i)
      }
      array.append(sum)
    }

    println(DenseMatrix(array.toArray))

    val v1 = DenseVector(0.0,1.0)
    val v2 = DenseVector(1.0,0.0)

    val v = v1 * v2.t

    var pmX1 = DenseVector(0.0,0.0)
    pmX1 = pmX1 :+ 1.0
    println(pmX1)

  }
}
