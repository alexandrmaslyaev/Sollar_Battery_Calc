package euler

import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.exp

fun fex(x: Double): Double = x + exp(-x)

fun f(x: Double, y: Double): Double = -y + x + 1

fun main() {
    val x = Array(100) { 0.0 }
    val z = Array(100) { 0.0 }
    val yel = Array(100) { 0.0 }
    val yel1 = Array(100) { 0.0 }
    val erel1 = Array(100) { 0.0 }
    val erel = Array(100) { 0.0 }
    var h = 0.001

    yel[0] = 1.0
    yel1[0] = 1.0

    x[0] = 0.0
    z[0] = 0.0
    println("X Yell error (e1)")
    for (index in 1 until 40) {
        x[index] = x[index - 1] + h
        yel[index] = yel[index - 1] + h * f(x[index - 1], yel[index - 1])
        erel[index] = abs((fex(x[index])-yel[index])/fex(x[index]))
        println("${x[index].bigDecimal()}    ${yel[index].bigDecimal()}    ${erel[index].bigDecimal()}")
    }
    h /= 2
    for (index in 1 until 40) {
        z[index] = z[index - 1] + h
        yel1[index] = yel1[index - 1] + h * f(z[index - 1], yel1[index - 1])
        erel1[index] = abs((fex(z[index]) - yel1[index]) / fex(z[index]))
        println("${x[index].bigDecimal()}    ${yel1[index].bigDecimal()}    ${erel[index].bigDecimal()}")
    }
    println("X Yel1 error(el)")
    for (index in 1 until 20) {
        println("${z[2 * index].bigDecimal()}    ${erel1[2 * index].bigDecimal() / erel[index].bigDecimal()}")
    }
}

private fun Double.bigDecimal() = this.toBigDecimal().round(MathContext(6, RoundingMode.HALF_UP))