package cn.haihan520.ipScan

import java.sql.{Connection, DriverManager, PreparedStatement}
object RddToJdbc {
  val writeToDatabase = (iterator: Iterator[(String, Int)]) => {
    var conn: Connection = null
    var ps : PreparedStatement = null
    val sql = "INSERT INTO gy_data (name,value) VALUES (?, ?)"
    try {
      conn = DriverManager.getConnection("jdbc:mysql://139.199.94.83:3306/gy?useUnicode=true&characterEncoding=UTF-8", "root", "hh940319")
      iterator.foreach(line => {
        ps = conn.prepareStatement(sql)
        ps.setString(1, line._1)
        ps.setInt(2, line._2)
        ps.executeUpdate()
      })
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }
  }
}
