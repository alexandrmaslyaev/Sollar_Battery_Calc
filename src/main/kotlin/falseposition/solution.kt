package falseposition

import kotlin.math.abs
import kotlin.math.exp

private fun Double.f(): Double = if (this == 0.0) -10000.0 else this - exp(1 / this)

private fun Double.falsePoint(b: Double): Double = b - b.f() * (b - this) / (b.f() - this.f())

fun main() {
    var pointA = 0.0
    var pointB = 0.0
    var answer: Double
    var pointsIsGood = false
    println("This is a program for calculating the false position root method")
    print("Please enter needed accuracy - ")
    val accuracy = readLine()?.toDouble() ?: 0.0
    println("Now the program will ask you to enter the interval in which the function crosses zero")
    while (!pointsIsGood) {
        print("Please enter point A - ")
        pointA = readLine()?.toDouble() ?: 0.0
        print("Please enter point B - ")
        pointB = readLine()?.toDouble() ?: 0.0
        if (pointA.f() * pointB.f() < 0) {
            pointsIsGood = true
            println("Success")
        } else {
            println("Failed")
        }
    }
    answer = pointA.falsePoint(pointB)
    while (abs(answer.f() - 0) > accuracy) {
        if (answer.f() * pointA.f() < 0) {
            pointB = answer
            answer = pointA.falsePoint(pointB)
        } else {
            pointA = answer
            answer = pointA.falsePoint(pointB)
        }
    }
    println("Answer = $answer")
}