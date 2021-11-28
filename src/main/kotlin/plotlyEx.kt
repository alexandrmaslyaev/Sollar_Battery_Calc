import space.kscience.plotly.*
import space.kscience.plotly.models.Trace
import space.kscience.plotly.models.functionXY

@UnstablePlotlyAPI
fun drawFunction(
    xs: Iterable<Number>,
    functions: List<Pair<(Double) -> Number, String>>,
    xAxisName: String = "",
    yAxisName: String = "",
    funTitle: String? = null
) {
    Plotly.plot {
        val tracesList = mutableListOf<Trace>()
        functions.forEach {
            tracesList.add(
                trace {
                    functionXY(xs, it.first)
                    name = it.second
                }
            )
        }
        traces(tracesList)
        layout {
            title = funTitle ?: functions.first().second
            xaxis { title = xAxisName }
            yaxis { title = yAxisName }
        }
    }.makeFile()
}