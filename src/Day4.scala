import scala.io.Source

/**
  * Created by covers1624 on 4/12/2017.
  */
object Day4 {

    def main(args: Array[String]) {
        println(Source.fromResource("day4.txt").getLines.map(_.split(" ")).count(p => p.diff(p.distinct).length == 0))
        println(Source.fromResource("day4.txt").getLines.map(_.split(" ")).filter(p => p.diff(p.distinct).length == 0).count(p => p.forall(str1 => p.forall(str2 => !(!str1.eq(str2) && str1.length == str2.length && str1.toCharArray.groupBy(identity).mapValues(_.length).toSet.diff(str2.toCharArray.groupBy(identity).mapValues(_.length).toSet).isEmpty)))))
    }
}
