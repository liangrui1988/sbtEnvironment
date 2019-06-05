import java.util.Properties

object Main extends App {
  println("xx")
  val prop = new Properties()
  val inputStream = this.getClass.getClassLoader.getResourceAsStream("localmysql.properties")
  prop.load(inputStream)
  println(prop)
  println(prop.getProperty("url"))

}
