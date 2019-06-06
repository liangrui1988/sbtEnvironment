import sbt.Keys._
import sbt._

/**
  * @author roy  create time 2019/06/05
  *         sbt打包多环境插件
  *
  */
object BuildEnvironmentsPlugin extends AutoPlugin {
  override def requires = plugins.JvmPlugin

  override def trigger = allRequirements


  object autoImport {
    lazy val Fuzz = config("dev") extend (Compile)
    lazy val Prod = config("prod") extend (Compile)
  }

  import autoImport._

  lazy val baseSettings: Seq[Def.Setting[_]] =
    Classpaths.configSettings ++
      Defaults.configSettings ++
      Seq(
        managedResourceDirectories := (managedResourceDirectories in Compile).value,
        managedSourceDirectories := (managedSourceDirectories in Compile).value,
        unmanagedSourceDirectories := (unmanagedSourceDirectories in Compile).value,
        unmanagedResourceDirectories := (unmanagedResourceDirectories in Compile).value,
      )

  lazy val devSettings: Seq[Def.Setting[_]] =
    baseSettings ++ Seq(
      unmanagedResourceDirectories += baseDirectory.value / "src" / Fuzz.name / "resources",
    )

  lazy val prodSettings: Seq[Def.Setting[_]] =
    baseSettings ++ Seq(
      unmanagedResourceDirectories += baseDirectory.value / "src" / Prod.name / "resources")


  override lazy val projectSettings = inConfig(Fuzz)(devSettings) ++ inConfig(Prod)(prodSettings)
}