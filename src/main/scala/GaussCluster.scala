import breeze.linalg.{DenseMatrix, DenseVector, det, inv}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object GaussCluster {
  var a1 = 1.0/3.0
  var a2 = 1.0/3.0
  var a3 = 1.0/3.0

  var cov1 = DenseMatrix((0.1, 0.0), (0.0, 0.1))
  var cov2 = DenseMatrix((0.1, 0.0), (0.0, 0.1))
  var cov3 = DenseMatrix((0.1, 0.0), (0.0, 0.1))

  var u1 = DenseVector(0.403, 0.237)
  var u2 = DenseVector(0.714, 0.346)
  var u3 = DenseVector(0.532, 0.472)

  var array = Array(0.0, 0.0, 0.0)
  var result = new collection.mutable.ArrayBuffer[Array[Double]]()
  def getPx(cov:DenseMatrix[Double],x:DenseVector[Double],u:DenseVector[Double]):Double = {
    val exp =  (mMv(inv(cov),(x-u))).t * u
    val px = (-0.5*Math.exp(exp)) / (2*Math.PI)*Math.sqrt(det(cov))
    px
  }

  def mMv(m:DenseMatrix[Double],v:DenseVector[Double]):DenseVector[Double] = {
    val cols = v.length
    val rows = m.rows
    val array = new collection.mutable.ArrayBuffer[Double]()
    val tv = v.t
    for(i <- 0 to cols - 1){
      var sum = 0.0
      for(j <- 0 to rows - 1){
        sum += tv(j)*m(j,i)
      }
      array.append(sum)
    }
//    println(array.toArray)
    DenseVector(array.toArray)
  }

  def getPm(x:DenseVector[Double]):Array[Double] = {
    val p1 = a1 * getPx(cov1,x,u1)
    val p2 = a2 * getPx(cov2,x,u2)
    val p3 = a3 * getPx(cov3,x,u3)

    val pm1 = p1 / (p1 + p2 + p3)
    val pm2 = p2 / (p1 + p2 + p3)
    val pm3 = p3 / (p1 + p2 + p3)

    array = Array(pm1,pm2,pm3)
//    var max = 0.0
//    for(i <- 0 to array.length-1){
//      if(array(i)>max){
//        max = array(i)
//      }
//    }

//    println(pm1 + ":" + pm2 + ":" + pm3)

    array
  }

  def updateCoef(x:Array[DenseVector[Double]]) = {
    val pmArray = new collection.mutable.ArrayBuffer[Array[Double]]()
    val rarray = new collection.mutable.ArrayBuffer[Array[Double]]()
    val m = x.length.toDouble
    for(i <- 0 to x.length-1){
      pmArray.append(getPm(x(i)))
      rarray.append(getPm(x(i)))
    }

    result = rarray

    var pmSum1 = 0.0
    var pmX1 = DenseVector(0.0,0.0)
    for(j <- 0 to pmArray.length-1){
      pmSum1 += pmArray(j)(0)
//      println((x(j) * pmArray(j)(0)))
      pmX1 = pmX1 :+ (x(j) * pmArray(j)(0))
    }
    u1 = pmX1 :/ pmSum1

    var pmSum2 = 0.0
    var pmX2 = DenseVector(0.0,0.0)
    for(j <- 0 to pmArray.length-1){
      pmSum2 += pmArray(j)(0)
      pmX2 =  pmX2 + (x(j) * pmArray(j)(1))
    }
    u2 = pmX2 :/ pmSum2

    var pmSum3 = 0.0
    var pmX3 = DenseVector(0.0,0.0)
    for(j <- 0 to pmArray.length-1){
      pmSum3 += pmArray(j)(0)
      pmX3 = pmX3 + (x(j) * pmArray(j)(2))
    }
    u3 = pmX3 :/ pmSum3

    a1 = pmSum1 / m
    a2 = pmSum2 / m
    a3 = pmSum3 / m

    var Ncov1 = DenseMatrix((0.0,0.0),(0.0,0.0))
    var Ncov2 = DenseMatrix((0.0,0.0),(0.0,0.0))
    var Ncov3 = DenseMatrix((0.0,0.0),(0.0,0.0))


    for(k <- 0 to x.length-1 ){
      Ncov1 = Ncov1 :+ pmArray(k)(0)*((x(k)-u1) * (x(k)-u1).t)
      Ncov2 = Ncov2 :+ pmArray(k)(1)*((x(k)-u1) * (x(k)-u2).t)
      Ncov3 = Ncov3 :+ pmArray(k)(2)*((x(k)-u1) * (x(k)-u3).t)

    }

    cov1 = Ncov1 :/ pmSum1
    cov2 = Ncov2 :/ pmSum2
    cov3 = Ncov3 :/ pmSum3


  }

  def getNewCov(pmSum:Double,pmArray:ArrayBuffer[Double],u:DenseVector[Double],x:Array[DenseVector[Double]]) = {
  }

  def main(args: Array[String]): Unit = {
//    val m = DenseMatrix((1.0,3.0),(2.0,4.0))
//    val v = DenseVector(1.0,2.0)
//    val temp = mMv(m,v)
//    val result = temp.t * v
//    println(result)
    val conf = new SparkConf().setMaster("local[4]").setAppName(s"${this.getClass.getSimpleName}")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val oriData = sc.textFile("C:\\Users\\dell\\Desktop\\data\\gaussCluster.txt")
    val data = oriData.map(_.split(" ")).map(s => s.map(_.toDouble)).map(s => {
      DenseVector(s(1),s(2))
    })
    var c = 0
    val x = data.collect()

    while(c<5){
      for (i <-0 to x.length-1){
        getPm(x(i))
      }
      updateCoef(x)
      c += 1
    }

    for (i <- 0 to result.length-1){
      val temp = result(i)
      var max = 0
      for (j <- 1 to temp.length-1){
        if(temp(j)>temp(max)){
          max = j
        }
      }
      println(max)
    }
  }

}