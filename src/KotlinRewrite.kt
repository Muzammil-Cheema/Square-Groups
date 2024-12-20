import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt

data class PointK(val name: String, var x: Double, var y: Double)

interface ShapeK {
    fun rotateBy(degrees: Int): ShapeK
    fun translateBy(x: Double, y: Double): ShapeK
    fun center(): PointK
    override fun toString(): String
}

class SquareK(vararg var vertices: PointK) : ShapeK {
    init {
        if (vertices.size < 4)
            throw IllegalArgumentException("Squares need 4 points.")
        vertices = vertices.sliceArray(0 until 4)
        require(isValid(*vertices)) { "Point ordering is invalid." }
        vertices = vertices.map { p -> round(p) }.toTypedArray()
    }

    private fun isValid(vararg vertices: PointK): Boolean {
        //Checks if all points are the same
        var count = 0
        for (i in 0..2) {
            if (vertices[i] === vertices[i + 1]) {
                count++
            }
        }
        if (count == 3) return true

        //Checks if square, then points ordering
        val a = vertices[0]
        val b = vertices[1]
        val c = vertices[2]
        val d = vertices[3]
        return distance(a, b) == distance(b, c) && distance(b, c) == distance(c, d) && distance(c, d) == distance(d, a) && a.x > b.x && a.x > c.x && a.y >= c.y && a.y > d.y && b.x <= d.x && b.y > d.y && c.x <= d.x
    }

    private fun distance(p1: PointK, p2: PointK): Double {
        return sqrt((p1.x - p2.x).pow(2.0) + (p1.y - p2.y).pow(2.0))
    }

    private fun round(p: PointK): PointK {
        val x = BigDecimal(p.x).setScale(2, RoundingMode.HALF_UP)
        val y = BigDecimal(p.y).setScale(2, RoundingMode.HALF_UP)
        return PointK(p.name, x.toDouble(), y.toDouble())
    }

    override fun rotateBy(degrees: Int): ShapeK {
        TODO("Not yet implemented")
    }

    override fun translateBy(x: Double, y: Double): ShapeK {
        return SquareK(*vertices.copyOf().map {v -> round(PointK(v.name, v.x+x, v.y+y))}.toTypedArray())
    }

    override fun center(): PointK {
        return PointK("center", vertices[1].x + (vertices[3].x - vertices[1].x) / 2, vertices[2].y + (vertices[0].y - vertices[2].y) / 2)
    }


    override fun toString(): String {
        val str = StringBuilder("[")
        for (v in vertices) {
            str.append(v).append("; ")
        }
        str.delete(str.length - 2, str.length).append("]\n")
        return str.toString()
    }

}