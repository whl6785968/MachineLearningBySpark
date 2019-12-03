import scala.collection.mutable.ArrayBuffer

object Softmax {

  def boltzmann(k:Int,q:ArrayBuffer[Double]):Double = {
    var accu = 0.0

    for(i <-0 to q.length-1){
      accu = accu + Math.exp((q(i)/0.5))
    }

    val p = Math.exp((q(k)/0.5))/accu

    p
  }


  def calcu(q:ArrayBuffer[Double],count:ArrayBuffer[Int]):Double = {

    var v = 0.0
    var r = 0.0
    val rewards = Array(1.0,-1.0,1.0,-1.0,2.0)

    for (i<-0 to 9){
      var max = 0
      val b = new collection.mutable.ArrayBuffer[Double]()
      for(j<- 0 to q.length-1){
        b.append(boltzmann(j,q))
      }

      for (k <-1 to b.length-1){
        if(b(k)>b(max)){
          max = k
        }
      }

      v = rewards(max)
      r = r + v
      q(max) = (q(max)*count(max)+v)/(count(max)+1)
      count(max) = count(max) + 1
    }
    r
  }

  def main(args: Array[String]): Unit = {
    val q = new collection.mutable.ArrayBuffer[Double]()
    q.append(0.0)
    q.append(0.0)
    q.append(0.0)
    q.append(0.0)
    q.append(0.0)

    val count = new collection.mutable.ArrayBuffer[Int]()
    count.append(0)
    count.append(0)
    count.append(0)
    count.append(0)
    count.append(0)

    val r = calcu(q,count)
    println("r: "+r)
  }


}
