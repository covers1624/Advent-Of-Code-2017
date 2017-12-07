import java.util

import scala.io.Source

/**
  * Created by covers1624 on 7/12/2017.
  */
object Day6 {


    def main(args: Array[String]) {
        val memory: Array[Int] = Source.fromResource("day6.txt").getLines().map(_.split("\t")).map(_.map(Integer.parseInt)).flatMap(_.toList).toArray
        var cache:util.List[String] = new util.ArrayList[String]

        var doTheDo = true
        var cycles = 0
        while (doTheDo) {
            var high = 0
            for (i <- memory.indices) {
                if (memory(i) > memory(high)) {
                    high = i
                }
            }
            var blocks = memory(high)
            memory(high) = 0
            while(blocks > 0) {
                high = (high + 1) % memory.length
                blocks -= 1
                memory(high) += 1
            }
            cycles += 1
            val hash = memory.mkString(",")
            if (cache.contains(hash)) {
                doTheDo = false
            }
            cache.add(hash)
            println(hash)
        }
        println(cycles)
        println(cycles - cache.indexOf(memory.mkString(",")) - 1)
    }
}
