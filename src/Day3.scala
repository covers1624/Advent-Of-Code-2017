import java.util.{HashSet => JHashSet, Set => JSet}

object Day3 {

    var SIZE = 800
    var magic_num = 368078

    var xOff = (Math.floor(SIZE / 2.0) - 1).intValue()
    var yOff = (Math.floor(SIZE / 2.0) - 1).intValue()

    def main(args: Array[String]) {
        var matrix = Array.ofDim[Int](SIZE, SIZE)
        var s = 1
        var d = 1
        var x = 0
        var y = 0
        var c = 1
        var found = false
        for (i <- 0 until SIZE - 1) {
            for (j <- 0 until (if (i < (SIZE - 1)) 2 else 3)) {
                for (k <- 0 until s) {
                    var v = 0
                    if (c == 1) {
                        v = c
                    }
                    for ((n_x, n_y) <- getNeighbours(xOff + x, yOff + y)) {
                        v += matrix(n_x)(n_y)
                    }
                    matrix(xOff + x)(yOff + y) = v
                    if (v > magic_num && !found) {
                        found = true
                        println(s"Fist greater than magic num found! $v")
                    }
                    if (c == magic_num) {
                        val aX = xOff + x
                        val aY = yOff + y
                        println(s"Magic num location, Array: $aX, $aY, Center Zero, $x, $y")
                    }

                    d match {
                        case 0 => y += 1;
                        case 1 => x += 1;
                        case 2 => y -= 1;
                        case 3 => x -= 1;
                    }
                    c += 1
                }
                d = (d + 1) % 4
            }
            s += 1
        }
    }

    def getNeighbours(x: Int, y: Int): Array[(Int, Int)] = {
        val neighbours: Array[(Int, Int)] = Array(//
            (-1, -1), (0, -1), (1, -1), //
            (-1, 0), (0, 0), (1, 0), //
            (-1, 1), (0, 1), (1, 1), //
        )
        var valid: JSet[(Int, Int)] = new JHashSet[(Int, Int)]()

        for ((x_n, y_n) <- neighbours) {
            if ((x + x_n < SIZE) && (x + x_n > 0) && (y + y_n < SIZE) && (y + y_n > 0)) {
                valid.add((x + x_n, y + y_n))
            }
        }
        valid.toArray(Array.ofDim(0))
    }

}
