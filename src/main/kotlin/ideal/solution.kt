import space.kscience.plotly.*
import space.kscience.plotly.models.functionXY
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.exp


@UnstablePlotlyAPI
fun main() {
    val us: MutableList<Double> = mutableListOf()
    var u = 0.0
    var j0 = 0.0
    var p = 0.0
    val h = 0.005
    var t = 298.0
    var j = 0.0
    val leftAlignFormat = "%10s |"
    val jsc = 35
    val j0Initial = 0.0025
    val vt = 0.0385

    val space = "          "
    val spaceJ0 = "         "

    print("${space}U|")
    print("${space}J|")
    print("${spaceJ0}J0|")
    print("${space}P|")
    print("${space}T|")
    println()
    while (j >= 0) {
        val context = MathContext(3, RoundingMode.HALF_UP)
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
        u += h
        j = jsc - j0Initial * (exp(u / vt) - 1)
        p = j * u
        t++
        j0 = jsc - j0Initial * (exp(0.0036 / (2.15e-4 * t)) - 1)
        println()
        us.add(u)
    }
    val functionJ: (Double) -> Number = {
        jsc - j0Initial * (exp(it / vt) - 1)
    }
    val functionT: (Double) -> Number = {
        t++
    }
    val functionP: (Double) -> Number = {
        val temp = jsc - j0Initial * (exp(it / vt) - 1)
        it * temp
    }
    drawFunction(
        xs = us,
        function = functionJ,
        functionName = "Зависимость тока от напряжения",
        xAxisName = "Напряжение (U)",
        yAxisName = "Ток (J)"
    )
    drawFunction(
        xs = us,
        function = functionT,
        functionName = "Зависимость температуры от напряжения",
        xAxisName = "Напряжение (U)",
        yAxisName = "Температура (T)"
    )
    drawFunction(
        xs = us,
        function = functionP,
        functionName = "Зависимость мощности от напряжения",
        xAxisName = "Напряжение (U)",
        yAxisName = "Мощность (P)"
    )
}

@UnstablePlotlyAPI
private fun drawFunction(
    xs: Iterable<Number>,
    function: (Double) -> Number,
    functionName: String,
    xAxisName: String,
    yAxisName: String,
) {
    val plot = Plotly.plot {
        trace {
            functionXY(xs, function)
            name = functionName
        }
        layout {
            title = functionName
            xaxis { title = xAxisName }
            yaxis { title = yAxisName }
        }
    }
    plot.makeFile()
}