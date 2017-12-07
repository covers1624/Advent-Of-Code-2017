import scala.collection.mutable
import scala.io.Source
import scala.util.control.Breaks._

/**
  * Created by covers1624 on 7/12/2017.
  */
object Day7 {

    def main(args: Array[String]) {
        val lines = Source.fromResource("day7.txt").getLines.toList
        val tree: mutable.Map[String, TreeNode] = mutable.Map()
        for (line <- lines) {
            val fs = line.indexOf(" ")
            val sep = line.indexOf("->")
            val wend = if (sep != -1) sep - 1 else line.length
            val name = line.substring(0, fs)
            val weight = Integer.parseInt(line.substring(fs + 2, wend - 1))
            val children: Array[String] = if (sep == -1) Array() else line.substring(sep + 3).split(", ")

            val node = tree.getOrElseUpdate(name, { new TreeNode(name, weight) })
            node.weight = weight
            for (child <- children) {
                val c = tree.getOrElseUpdate(child, { new TreeNode(child, -1) })
                c.parent = node
                node.children :+= c
            }
        }
        val root = tree.head._2.rootNode
        println(root.name)

        var unbalanced = root
        breakable {
            while (true) {
                val w = unbalanced.children.map(_.totalWeight)
                if (w.distinct.size != 1) {
                    unbalanced = unbalanced.children(w.indexOf(w.max))
                } else {
                    break
                }
            }
        }
        println(unbalanced.name)
        val w = root.children.map(_.totalWeight)
        println(unbalanced.weight - (w.max - w.min))
    }
}

class TreeNode(val name: String, var weight: Int, implicit var parent: TreeNode = null, implicit var children: List[TreeNode] = List()) {

    def rootNode: TreeNode = {
        parent match {
            case t: TreeNode => t.rootNode
            case _ => this
        }
    }

    def totalWeight: Int = weight + children.map(_.totalWeight).sum
}
