import space.kscience.plotly.UnstablePlotlyAPI
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.exp

@UnstablePlotlyAPI
fun main() {
    val us: MutableList<Double> = mutableListOf()
    var u = 0.0
    var j0 = 0.0
    var p = 0.0
    val h = 0.0005
    var t = 298.0
    var j = 0.0
    val jsc = 35
    val j0Initial = 25E-3
    val m = 1.5
    val kb = 1.38E-23
    val e = 1.6E-19

    printTableHeader()

    fun functionJWithM(currentM: Double): (Double) -> Number = {
        jsc - j0Initial * (exp((it * e) / (currentM * kb * t)) - 1)
    }

    val functionJ: (Double) -> Number = {
        jsc - j0Initial * (exp((it * e) / (m * kb * t)) - 1)
    }

    val functionP: (Double) -> Number = {
        val temp = jsc - j0Initial * (exp((it * e) / (m * kb * t)) - 1)
        it * temp
    }

    val functionT: (Double) -> Number = {
        t++
    }

    while (j >= 0) {
        val context = MathContext(3, RoundingMode.HALF_UP)
        printResults(u, context, j, j0, p, t)
        u += h
        j = functionJ(u).toDouble()
        p = functionP(u).toDouble()
        t = functionT(u).toDouble()
        j0 = jsc - j0Initial * (exp(0.0036 / (2.15e-4 * t)) - 1)
        us.add(u)
    }

    us.removeLast()

    drawFunction(
        xs = us,
        functions = listOf(
            Pair(functionJWithM(1.5), "m = 1.5"),
            Pair(functionJWithM(1.6), "m = 1.6"),
            Pair(functionJWithM(1.7), "m = 1.7"),
            Pair(functionJWithM(1.8), "m = 1.8"),
            Pair(functionJWithM(1.9), "m = 1.9"),
            Pair(functionJWithM(2.0), "m = 2.0"),
            Pair(functionJWithM(2.1), "m = 2.1"),
            Pair(functionJWithM(2.2), "m = 2.2"),
            Pair(functionJWithM(2.3), "m = 2.3"),
            Pair(functionJWithM(2.4), "m = 2.4"),
            Pair(functionJWithM(2.5), "m = 2.5")
        )
    )

    drawFunctions(us, functionJ, functionP)
}

@UnstablePlotlyAPI
private fun drawFunctions(
    us: MutableList<Double>,
    functionJ: (Double) -> Number,
    functionP: (Double) -> Number
) {
    drawFunction(
        xs = us,
        functions = listOf(Pair(functionJ, "Зависимость тока от напряжения")),
        xAxisName = "Напряжение (U)",
        yAxisName = "Ток (J)"
    )
    drawFunction(
        xs = us,
        functions = listOf(Pair(functionP, "Зависимость мощности от напряжения")),
        xAxisName = "Напряжение (U)",
        yAxisName = "Мощность (P)"
    )
}

private fun printResults(
    u: Double,
    context: MathContext,
    j: Double,
    j0: Double,
    p: Double,
    t: Double,
) {
    val leftAlignFormat = "%10s |"

    val resultU = u.toBigDecimal().round(context)
    val resultJ = j.toBigDecimal().round(context)
    val resultJ0 = j0.toBigDecimal().round(context)
    val resultP = p.toBigDecimal().round(context)
    val resultT = t.toBigDecimal().round(context)

    System.out.format(leftAlignFormat, resultU)
    System.out.format(leftAlignFormat, resultJ)
    System.out.format(leftAlignFormat, resultJ0)
    System.out.format(leftAlignFormat, resultP)
    System.out.format(leftAlignFormat, resultT)

    println()
}

private fun printTableHeader() {
    val space = "          "
    val spaceJ0 = "         "
    print("${space}U|")
    print("${space}J|")
    print("${spaceJ0}J0|")
    print("${space}P|")
    print("${space}T|")
    println()
}