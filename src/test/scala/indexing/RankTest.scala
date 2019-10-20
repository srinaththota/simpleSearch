package indexing
import org.scalatest._
import rank.Rank
import results.Results

import scala.collection.mutable

class RankTest extends FlatSpec {

"Rank" should "return double value" in{
val details=new mutable.LinkedHashMap[String,mutable.LinkedHashMap[String,Int]]
  val countdetails=new mutable.LinkedHashMap[String,Int]
  countdetails.put("SampleFile",1)
  details.put("TestData",countdetails)
  println(details)

 assert(Indexing.findWordMatch("TestData","SampleFile",details)==100)



}
  "Index" should "return list of Files" in {
    val details=new mutable.LinkedHashMap[String,mutable.LinkedHashMap[String,Int]]
    val countdetails=new mutable.LinkedHashMap[String,Int]
    countdetails.put("SampleFile",1)
    details.put("TestData",countdetails)
    println(details)
    assert(Indexing.retreiveFilesContainingWords("TestData",details)==List("SampleFile"))
  }
}
