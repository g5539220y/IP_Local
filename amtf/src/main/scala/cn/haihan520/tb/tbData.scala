package cn.haihan520.tb

import cn.haihan520.ipScan.IpTool
import org.apache.spark.{SparkConf, SparkContext}

object tbData {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("tbData")
      .set("spark.driver.host", "127.0.0.1").set("spark.driver.port", "7077")
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("C:\\access.log.fensi").map(line => {
      val fields = line.split(" ")
      fields(0)
    })
    val res = rdd.map(ip=> {
      val ipnum = IpTool.iptoLong(ip)
    })
    //println(res.toBuffer)


  }
}
