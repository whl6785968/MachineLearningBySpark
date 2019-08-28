import org.apache.spark.ml.feature.Binarizer
import org.apache.spark.sql.SparkSession

object Binarizer {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[4]").appName("binarizer").getOrCreate()

    val data = Array((0, 0.1), (1, 0.8), (2, 0.2))
    val dataFrame = spark.createDataFrame(data).toDF("id", "feature")

    val binarizer:Binarizer = new Binarizer()
      .setInputCol("feature")
      .setOutputCol("binary_feature")
      .setThreshold(0.5)

    val bdf = binarizer.transform(dataFrame)

    println(s"Binarizer output with Threshold = ${binarizer.getThreshold}")
    bdf.show()
  }

}
