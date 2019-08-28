import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Graph_test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[4]").setAppName(s"${this.getClass.getSimpleName}")

    val sc = new SparkContext(conf)

    // Create an RDD for the vertices
    val users: RDD[(VertexId, (String, String))] =
      sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
        (5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))

    // Create an RDD for edges
    val relationships: RDD[Edge[String]] =
      sc.parallelize(Array(Edge(3L, 7L, "collab"),Edge(5L, 3L, "advisor"),
        Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))

    // Define a default user in case there are relationship with missing user
    val defaultUser = ("John Doe", "Missing")

    val graph =  Graph(users,relationships,defaultUser)

    graph.vertices.filter{ case(id,(name,pos)) => pos == "postdoc" }.count
    graph.edges.filter( e => e.srcId>e.dstId).count

    //triplet类似数据库的三表连接，即源表、目的地表、边表连接
    //通过triplet即可找到边里对应的点和其属性
    val facts: RDD[String] = graph.triplets.map(triplet => triplet.srcAttr._1 + " is the "+ triplet.attr+ " of " + triplet.dstAttr._1)
    facts.collect.foreach(println(_))

    val degrees = graph.inDegrees
    println(degrees)

    val input_graph:Graph[Int,String] = graph.outerJoinVertices(graph.outDegrees)((vid,_,degopt) => degopt.getOrElse(0))
    val outputGraph: Graph[Double, Double] = input_graph.mapTriplets(triplet => 1.0 / triplet.srcAttr).mapVertices((id, _) => 1.0)

    input_graph.triplets.map(triplet => triplet.srcAttr).collect().foreach(println)
  }
}
