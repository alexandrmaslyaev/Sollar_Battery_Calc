package CW.ideal

import kotlin.math.abs
import kotlin.math.exp


const val e = 1.6E-19
const val kb = 1.38E-23
const val Jsc = 37E-3
const val J0 = 25E-7
const val acc = 0.00001
const val m = 1.5
const val T = 298
const val A = 1
const val Rs = 0.025
const val Rsh = 2500.0

fun main() {
    val arrayU = Array(1000) { 0.0 }
    val arrayJ = Array(1000) { 0.0 }

    val h = 0.01
    for (i in 0 until 1000) {
        arrayU[i] = h * i
    }
    newtonRapson(arrayU, arrayJ)
    simiter(arrayU, arrayJ)
}

fun ideal(u: Double): Double = Jsc - J0 * (exp((u * e) / (m * kb * T)) - 1)
fun real(u: Double, j: Double) =
    (Jsc - J0 * (exp((e * (u + j * A * Rs)) / (m * kb * T)) - 1) - (u + j * A * Rs) / (A * Rsh) - j)

fun diff(u: Double, j: Double) =
    (J0 * exp((e * (u + j * A * Rs)) / (m * kb * T)) * (e * A * Rs / (m * kb * T)) - Rs / Rsh - 1)

fun g(U: Double, J: Double) = Jsc - J0 * (exp(e * (U + J * A * Rs) / (m * kb * T)) - 1) - (U + J * A * Rs) / (A * Rsh)

fun newtonRapson(x: Array<Double>, y: Array<Double>) {
    var p = 0
    var x0: Double
    var root: Double
    println("Newton Rapson")

    for (i in 0 until 1000) {
        x0 = ideal(x[i])
        root = x0
        while (abs(real(x[i], root)) > acc && p < 100) {
            root -= real(x[i], root) / diff(x[i], root)
            p++
        }
        if (root > 0) {
            y[i] = root
            println(y[i])
        }
    }

    println("Newton Rapson PV")
    for (i in 0 until 1000) {
        x0 = ideal(x[i])
        root = x0
        while (abs(real(x[i], root)) > acc && p < 100) {
            root -= real(x[i], root) / diff(x[i], root)
            p++
        }
        if (root > 0) {
            y[i] = root
            println(x[i] * y[i])
        }
    }
}

fun simiter(x: Array<Double>, y: Array<Double>) {
    var x0: Double
    var p = 0
    var root: Double
    println("Simple iterations")
    for (i in 0 until 1000) {
        x0 = ideal(x[i])
        root = x0
        while (abs(real(x[i], root)) > acc && p < 100) {
            root = g(x[i], root)
            p++
        }
        if (root > 0) {
            y[i] = root
            println(y[i])
        }
    }
}
