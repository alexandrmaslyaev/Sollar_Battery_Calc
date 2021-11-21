package ideal

import kotlin.math.abs
import kotlin.math.exp

fun funResult(value: Double): Double = 35 - 0.0025 * (exp(value / 0.038) - 1) - 0.06173 * value * exp(value / 0.038)

fun difResult(value: Double): Double = -0.0526 * (exp(value / 0.038)) - 1.62447 * value * exp(value / 0.038)

fun gFunResult(value: Double): Double = value - funResult(value) / difResult(value)

fun main() {
    println("Введите приблизительное значение корня")
    var root = readLine()?.toDouble()

    println("Введите желаемую точность")
    val eps: Double? = readLine()?.toDouble()

    if (eps != null && root != null) {
        var count = 0L
        while (eps < abs(funResult(value = root!!))) {
            if (difResult(root) == 0.0) break
            print("$count  ")
            print("$root  ")
            print("${gFunResult(root)}  ")
            print("${difResult(root)}  ")
            print("${funResult(root)}  ")
            root = gFunResult(root)
            count++
            println()
        }
    }
}