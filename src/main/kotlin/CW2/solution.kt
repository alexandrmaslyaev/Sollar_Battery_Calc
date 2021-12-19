package CW2

import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.sin

fun main() {
    var x: Double
    val t = Array(300) { 0.0 }
    val b = Array(300) { 0.0 }

    val tol = 0.001 // точность
    val e = 0.8
    val h = 0.1;
    b[0] = 0.0
    t[0] = 0.0 //начальные условия

    for (i in 1 until 200) { // организация улучшенного метода Эйлера
        x = b[i - 1] + h * calc(b[i - 1], e)
        b[i] = b[i - 1] + (h / 2) * (calc(b[i - 1], e) + calc(x, e))
        t[i] = t[i - 1] + i * h
        if (abs(asin(e) - b[i]) < tol)
        {
            println("t = ${t[i]} B = ${b[i]}")
            break; //выход из цикла при достижении насыщения
        }
        else
        {
            println("${b[i]}")
        }
    }

}
private fun calc(b: Double, e: Double) : Double = e - sin(b)

