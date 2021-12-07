import space.kscience.plotly.UnstablePlotlyAPI
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.exp

val us: MutableList<Double> = mutableListOf()
var u = 0.0
var j0 = 0.0
var p = 0.0
const val h = 0.005
var t = 298.0
var j = 0.0
const val jsc = 35
const val j0Initial = 25E-3
const val m = 2
const val kb = 1.38E-23
const val e = 1.6E-19
const val A = 1
var Rs = 0.025
var Rsh = 2500.0

@UnstablePlotlyAPI
fun main() {

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
        j0 = jsc - j0Initial * (exp((u * e) / (m * kb * t)) - 1)
        us.add(u)
    }

    var p = 0

    for (i in us.indices) {
        var currentJ = functionJ(us[i]).toDouble()
        while (abs(functionJReal(us[i], currentJ)) > 0 && p < 100) {
            currentJ -= functionJReal(us[i], currentJ) / diff(us[i], currentJ)
            p++
            if (currentJ > 0) {
                println("J0 newton rapson = $currentJ")
            }
        }
    }

    for (i in us.indices) {
        var currentJ = functionJ(us[i]).toDouble()
        while (abs(functionJReal(us[i], currentJ)) > 0 && p < 100) {
            currentJ -= g(us[i], currentJ)
            p++
            if (j0 > 0) {
                println("J0 simpl it = $j0")
            }
        }
    }

    us.removeLast()

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

private fun functionJReal(u: Double, j: Double): Double {
    return jsc - j0Initial * (exp((e * (u + j * A * Rs)) / (m * kb * t)) - 1) - ((u + j * A * Rs) / (A * Rsh))
}

private fun diff(u: Double, j: Double): Double {
    return (j0Initial * exp((e * (u + j * A * Rs)) / (m * kb * t)) * (e * A * Rs / (m * kb * t)) - Rs / Rsh - 1)
}

private fun g(u: Double, j: Double): Double {
    return jsc - j0Initial * (exp((e * (u + j * A * Rs)) / (m * kb * t)) - 1) - (u + j * A * Rs) / (A * Rsh)
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