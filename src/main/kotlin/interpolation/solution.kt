package interpolation

import drawFunction
import space.kscience.plotly.UnstablePlotlyAPI
import kotlin.math.pow

private fun interpolation(z: Double, N: Int, x: List<Double>, f: List<Double>): Double {
    var result = 0.0
    val l: Array<Double> = Array(100) { 0.0 }

    for (i in 0 until N) {
        l[i] = 1.0
        for (j in 0 until N) {
            if (j != i) l[i] = l[i] * ((z - x[j]) / (x[i] - x[j]))
        }
        result += l[i] * f[i]
    }
    return result
}

fun getRealPartEps(realPart: List<Double>, imaginaryPart: List<Double>): Pair<List<Double>, List<Double>> {
    val values: Pair<MutableList<Double>, MutableList<Double>> = Pair(mutableListOf(), mutableListOf())

    for (i in realPart.indices) {
        values.first.add(realPart[i].pow(2.0) - imaginaryPart[i].pow(2.0))
        values.second.add(2 * realPart[i] * imaginaryPart[i])
    }
    return values
}


@UnstablePlotlyAPI
fun printInterpolEpsilon(
    waveLength: List<Double>,
    realPart: List<Double>,
    imaginaryPart: List<Double>,
    name: String
) {

    val epsilonParts = getRealPartEps(realPart, imaginaryPart)
    var temp = waveLength[0]

    var i = 0
    val x2: MutableList<Double> = mutableListOf()

    while (temp <= waveLength[waveLength.size - 1]) {
        x2.add(temp)
        temp += 0.01
        i++
    }

    val functionForReal: (Double) -> Number = {
        interpolation(it, waveLength.size, waveLength, epsilonParts.first)
    }
    val funForImg: (Double) -> Number = {
        interpolation(it, waveLength.size, waveLength, epsilonParts.second)
    }

    drawFunction(
        xs = x2,
        functions = listOf(Pair(functionForReal, "Real $name"), Pair(funForImg, "Ideal $name")),
        funTitle = "Epsilon graph $name"
    )
}

@UnstablePlotlyAPI
fun printInterpolNPart(
    waveLength: List<Double>,
    realPart: List<Double>,
    imaginaryPart: List<Double>,
    name: String
) {
    var temp = waveLength[0]

    val x2 = mutableListOf<Double>()

    while (temp <= waveLength[waveLength.size - 1]) {
        x2.add(temp)
        temp += 0.01
    }

    val functionForReal: (Double) -> Number = {
        interpolation(it, waveLength.size, waveLength, realPart)
    }
    val funForImg: (Double) -> Number = {
        interpolation(it, waveLength.size, waveLength, imaginaryPart)
    }
    drawFunction(
        xs = x2,
        functions = listOf(Pair(functionForReal, "Real $name"), Pair(funForImg, "Ideal $name")),
        funTitle = "N graph $name"
    )
}