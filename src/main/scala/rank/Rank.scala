package rank
import java.io.File

import indexing.Indexing
import results.Results
import search.Program.Index

import scala.collection.mutable
import scala.util.Sorting

class Rank {

  def queryMeth(input:String,indexedFiles:mutable.LinkedHashMap[String,mutable.LinkedHashMap[String,Int]]): List[Results] ={

    val files=Indexing.retreiveFilesContainingWords(input,indexedFiles)

   files.map(eachFile => query(input, eachFile,indexedFiles)).sortWith(_.score > _.score)
  }


  def query(query: String, file:String,indexedFiles:mutable.LinkedHashMap[String,mutable.LinkedHashMap[String,Int]]): Results = {

    val score=Indexing.findWordMatch(query,file,indexedFiles)
    val res=new Results(file,score)
    res
   // val score = index.similarity(query, document)
    //new Result[T](document, score)
  }
}
