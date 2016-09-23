name := "learn2play"

version := "0.0.1"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)

libraryDependencies ++= Seq(
	cache,
	javaWs,
	"com.google.code.gson" % "gson" % "2.7",
	"org.jongo" % "jongo" % "1.3.0",
	"org.mongodb" % "mongo-java-driver" % "2.14.3" // version 3 is not supported by Jongo
)
