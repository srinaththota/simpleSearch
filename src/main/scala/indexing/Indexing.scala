package indexing
import java.io.File
import scala.collection.mutable
import scala.collection.mutable.LinkedHashMap

object Indexing {

  def getCountOfFilesAndWords(file: File): LinkedHashMap[String,LinkedHashMap[String,Int]]= {
    //Parse each File and put words as key and their occurance as count
    val files:Array[File]=file.listFiles()

    val details = new LinkedHashMap[String, LinkedHashMap[String,Int]]

    var count=0
    files.foreach {

      file => {
        count += 1
        val list = io.Source.fromFile(file).getLines()
          .flatMap(_.split(" "))
          .map(word=>word.filter(Character.isLetter(_)))// excluded "--" as some files are looking in the form of table with these characters
          .toList
        list.foreach { word =>
          val x = details.getOrElseUpdate(word, LinkedHashMap(file.toString -> 0))
          x.put(file.toString, x.get(file.toString).getOrElse(0) + 1) // i maintained count of word occurance . we can ignore
        }
      }
    }
    println(count+" files read in directory "+file)
    details
  }

  //extract files where the word is in .
  def retreiveFilesContainingWords(query:String,indexedDetails:LinkedHashMap[String,LinkedHashMap[String,Int]]):List[String] ={
    val listOfWords=query.split(" ").toList
    //val files=listOfWords.map(word => indexedDetails.get(word).getOrElse(Nil).map(x => x._1).toList).flatten.distinct
    val files=listOfWords.map(word => indexedDetails.get(word).getOrElse(Nil).map(x=>x._1).toList).flatten.distinct
    files
  }
  // map all words and find files with their occurance
  def findWordMatch(query:String , eachFile:String,indexedFiles:mutable.LinkedHashMap[String,mutable.LinkedHashMap[String,Int]]): Double = {

      var count = 0
      val listOfwords = query.split(" ").toList
      listOfwords.foreach {
        word => {
          val filesMap = indexedFiles.get(word).getOrElse(LinkedHashMap(""->0))
          if (filesMap.contains(eachFile)) {
            count += 1
          }
        }
      }
      (count*100) / listOfwords.size
    }
}
