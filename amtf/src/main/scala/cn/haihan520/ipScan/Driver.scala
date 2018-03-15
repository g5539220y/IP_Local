package cn.haihan520.ipScan

import org.apache.spark.{SparkConf, SparkContext}

object Driver {
  def main(args: Array[String]): Unit = {
    val RulesFile = args(0)
    val LogFile = args(1)
    //本地运行
     val conf = new SparkConf().setMaster("local[2]").setAppName("Gy_company")
      .set("spark.driver.host", "127.0.0.1").set("spark.driver.port", "7077")
    //val conf = new SparkConf().setAppName("Gy_company").setMaster("spark://haihan:7077")
    val sc = new SparkContext(conf)
    val ip_RulesRDD = sc.textFile(RulesFile).map(line =>{
      val fields = line.split("\\|")
      val start_num = fields(2)
      val end_num = fields(3)
      val province = fields(6)
      (start_num, end_num, province)
    })
    val ip_Rules = ip_RulesRDD.collect()
    val ipRules_Broadcast = sc.broadcast(ip_Rules)
    val ipsRDD = sc.textFile(LogFile).map(line => {
      val fields = line.split("\\|")
      fields(1)
    })
    val resultRDD = ipsRDD.map(ip => {
      val ipNum = IpTool.iptoLong(ip)
      val index = IpTool.dichotomy(ipRules_Broadcast.value, ipNum)
      val info = ipRules_Broadcast.value(index)
      info
    }).map(t => (t._3, 1)).reduceByKey(_+_)
    resultRDD.foreachPartition(RddToJdbc.writeToDatabase(_))
    sc.stop()
  }
}
