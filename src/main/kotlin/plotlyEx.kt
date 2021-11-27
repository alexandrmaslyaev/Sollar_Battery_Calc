import space.kscience.plotly.*
import space.kscience.plotly.models.functionXY

@UnstablePlotlyAPI
fun drawFunction(
    xs: Iterable<Number>,
    function: (Double) -> Number,
    function2: ((Double) -> Number)? = null,
    functionName: String,
    functionName2: String? = null,
    xAxisName: String = "",
    yAxisName: String = "",
    funTitle: String? = null
) {
    Plotly.plot {
        val tracesList = mutableListOf(
            trace {
                functionXY(xs, function)
                name = functionName
            },
        )
        if (function2 != null)
            tracesList.add(
                trace {
                    functionXY(xs, function2)
                    name = functionName2
                }
            )
        traces(tracesList)
        layout {
            title = funTitle ?: functionName
            xaxis { title = xAxisName }
            yaxis { title = yAxisName }
        }
    }.makeFile()
}