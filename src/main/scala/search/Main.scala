
package search

import java.io.File

import rank._

import scala.collection.mutable
import scala.util.Try
import indexing.Indexing

object Main extends App {

  Program.readFile(args).fold(
    println,
    file => Program.iterate(Program.index(file))
  )
}

object Program {

  sealed trait ReadFileError

  case object MissingPathArg extends ReadFileError

  case class NotDirectory(error: String) extends ReadFileError

  case class FileNotFound(t: Throwable) extends ReadFileError

  case class Index(index: mutable.LinkedHashMap[String,mutable.LinkedHashMap[String, Int]])
  def readFile(args: Array[String]): Either[ReadFileError, File] = {

    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new java.io.File(path)).fold(
        throwable => Left(FileNotFound(throwable)),
        file =>
          if (file.isDirectory) Right(file)
          else Left(NotDirectory(s"Path [$path] is not a directory"))
      )
    } yield file
  }
  def index(file:File):mutable.LinkedHashMap[String,mutable.LinkedHashMap[String, Int]]={
    val details=Indexing.getCountOfFilesAndWords(file)
    details
  }

  def iterate(indexedFiles:mutable.LinkedHashMap[String,mutable.LinkedHashMap[String,Int]]):Unit= {

    print(s"search>")
    val query = scala.io.Source.stdin.bufferedReader().readLine()
    println(query)
    if (query.equals(":quit")) {
      println("SEARCH ENDED")
    }
    else {
      //find rank in indexed files
      val result = new Rank().queryMeth(query, indexedFiles)
      result.foreach {
        res =>
          print(res)
          print(" ")
      }
      println()
      iterate(indexedFiles)
    }
  }
}