package cn.haihan520.ipScan


object IpTool {
  def iptoLong(ip: String): Long = {
    val num = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until num.length){
      ipNum =  num(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def dichotomy(lines: Array[(String, String, String)], ip: Long) : Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      val high_ipnum = lines(middle)._2.toLong
      val low_ipnum = lines(middle)._1.toLong
      if ((ip >= low_ipnum) && (ip <= high_ipnum))
        return middle
      if (ip < low_ipnum)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }
}
