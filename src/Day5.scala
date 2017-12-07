import scala.io.Source

/**
  * Created by covers1624 on 5/12/2017.
  */
object Day5 {

    def main(args: Array[String]) {
        val part2 = true
        var steps = 0
        var input: Array[Int] = Source.fromResource("day5.txt").getLines().toArray.map(Integer.parseInt)

        var inMaze = true
        var pointer = 0
        while (inMaze) {
            steps += 1
            val j = input(pointer)
            if (j >= 3 && part2) {
                input(pointer) = j - 1
            } else {
                input(pointer) = j + 1
            }
            pointer += j
            if (pointer >= input.length) {
                inMaze = false
            }
        }
        println(steps)

    }

}
