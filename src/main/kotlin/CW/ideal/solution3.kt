package CW.ideal

import kotlin.math.abs
import kotlin.math.exp


const val e = 1.6E-19
const val kb = 1.38E-23
const val Jsc = 37E-3
const val J0 = 25E-7
const val acc = 0.00001
const val m = 2
const val T = 298
const val A = 1
const val Rs = 0.05
const val Rsh = 15.0

fun main() {
    var us = Array(1000) { 0.0 }
    val js = Array(1000) { 0.0 }

    us = us.setUs()
    calculateIdeal(us)
    newtonRapson(us, js)
    simpleIterations(us, js)
}

private fun Array<Double>.setUs(): Array<Double> {
    val h = 0.01
    for (i in 0 until 1000) {
        this[i] = h * i
    }
    return this
}

private fun calculateIdeal(us: Array<Double>) {
    println("Ideal, when m = $m")
    for (i in us.indices) {
        val currentJ = ideal(us[i])
        if (currentJ >= 0) {
            println("U = ${currentJ * us[i]}")
            println("J = $currentJ")
        }
    }
}

private fun ideal(u: Double): Double = Jsc - J0 * (exp((u * e) / (m * kb * T)) - 1)
private fun real(u: Double, j: Double) =
    (Jsc - J0 * (exp((e * (u + j * A * Rs)) / (m * kb * T)) - 1) - (u + j * A * Rs) / (A * Rsh) - j)

private fun diff(u: Double, j: Double) =
    (J0 * exp((e * (u + j * A * Rs)) / (m * kb * T)) * (e * A * Rs / (m * kb * T)) - Rs / Rsh - 1)

private fun g(U: Double, J: Double) =
    Jsc - J0 * (exp(e * (U + J * A * Rs) / (m * kb * T)) - 1) - (U + J * A * Rs) / (A * Rsh)

private fun newtonRapson(us: Array<Double>, js: Array<Double>) {
    var currentJ: Double
    println("Newton Rapson")
    println("Rs = $Rs")

    for (i in 0 until 1000) {
        currentJ = ideal(us[i])
        while (abs(real(us[i], currentJ)) > acc) {
            currentJ -= real(us[i], currentJ) / diff(us[i], currentJ)
        }
        if (currentJ > 0) {
            js[i] = currentJ
            println("J = ${js[i]}")
            println("U = ${js[i] * us[i]}")
        }
    }
}

private fun simpleIterations(us: Array<Double>, js: Array<Double>) {
    var currentJ: Double

    println("Simple iterations")
    println("Rs = $Rs")
    for (i in 0 until 1000) {
        currentJ = ideal(us[i])
        while (abs(real(us[i], currentJ)) > acc) {
            currentJ = g(us[i], currentJ)
        }
        if (currentJ > 0) {
            js[i] = currentJ
            println("J = ${js[i]}")
            println("U = ${js[i] * us[i]}")
        }
    }
}
