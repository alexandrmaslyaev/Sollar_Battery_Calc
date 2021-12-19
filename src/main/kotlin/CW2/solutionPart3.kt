package CW2

import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.sin

fun main() {
    var x: Double
    val t = Array(1000) { 0.0 }
    val b = Array(1000) { 0.0 }
    val e = 0.3 // e=w/wc
    val tol = 0.002 //точность
    val h = 0.1
    b[0] = 0.0
    t[0] = 0.0 //начальные условия
    for (j in 0 until 100) {
        b[0] = j * 0.1
        for (i in 1 until 100) {
            x = b[i - 1] + h * function(b[i - 1], e)
            b[i] = b[i - 1] + (h / 2) * (function(b[i - 1], e) + function(x, e))
            t[i] = t[i - 1] + i * h
            if (abs(asin(e) - b[i]) < tol)
            {
                println("${t[i]}")
                break
            }
        }
    }
}
private fun function(B: Double, e: Double): Double = e - sin(B)