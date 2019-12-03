import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Egreedy {


  def getMaxIndex(q:ArrayBuffer[Double]):Int = {
    var max = 0
    for(i <-1 to q.length-1){
      if(q(i)>q(max)){
        max = i
      }
    }
    max
  }



  def main(args: Array[String]): Unit = {
    var r = 0.0
    val e = 0.1
    var v = 0.0
    val rewards = Array(1.0,-1.0,1.0,-1.0,2.0)

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

    for (i <-0 to 100){
      val random = new Random()
      val rand = random.nextDouble()
      var k = 0
      if (rand<e){
        k = random.nextInt(5)
      }
      else {
        k = getMaxIndex(q)
      }
      println("k: " + k)

      v = rewards(k)
      r = r + v

      q(k) = (q(k)*count(k)+v)/(count(k)+1)
      count(k) = count(k) + 1

    }

    println("r: "+r)
  }

}
