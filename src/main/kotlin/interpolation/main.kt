package interpolation

import space.kscience.plotly.UnstablePlotlyAPI

@UnstablePlotlyAPI
fun main() {
    //n^2 - k^2 = realPart epsilon => realPart^2 - imaginaryPart^2
    //2nk = imagePart epsilon => 2*realPart * imaginaryPart

    /*AU*/
    val waveLengthAu: List<Double> = listOf(0.4, 0.5, 0.652, 0.73, 0.85, 0.918, 1.033)
    val realPartAu: List<Double> = listOf(1.65, 0.916, 0.166, 0.164, 0.198, 0.222, 0.272)
    val imaginaryPartAu: List<Double> = listOf(1.956, 1.840, 3.15, 4.35, 5.63, 6.168, 7.07)
    /*Ag*/
    val waveLengthAg: List<Double> = listOf(0.405, 0.5, 0.664, 0.75, 0.85, 0.918, 1.033)
    val realPartAg: List<Double> = listOf(0.173, 0.13, 0.14, 0.146, 0.152, 0.18, 0.22)
    val imaginaryPartAg: List<Double> = listOf(1.95, 2.974, 4.15, 4.908, 5.72, 6.183, 6.99)
    /*Cr data source 1*/
    val waveLengthCr: List<Double> = listOf(0.405, 0.5, 0.664, 0.75, 0.85, 0.918, 1.033)
    val realPartCr: List<Double> = listOf(1.48, 2.49, 3.48, 3.84, 4.31, 4.42, 4.5)
    val imaginaryPartCr: List<Double> = listOf(3.54, 4.44, 4.36, 4.37, 4.32, 4.3, 4.2)
    /*Cr data source 2*/
    val waveLengthCr2: List<Double> = listOf(0.405, 0.5, 0.664, 0.75, 0.85, 0.918, 1.033)
    val realPartCr2: List<Double> = listOf(1.55, 1.78, 2.29, 2.65, 3.08, 3.23, 3.35)
    val imaginaryPartCr2: List<Double> = listOf(2.15, 2.39, 3.05, 3.24, 3.31, 3.26, 3.3)
    /*Fe*/
    val waveLengthFe: List<Double> = listOf(0.62, 0.653, 0.729, 0.827, 0.954, 1.03, 1.13)
    val realPartFe: List<Double> = listOf(2.86, 2.89, 2.98, 3.05, 3.16, 3.24, 3.33)
    val imaginaryPartFe: List<Double> = listOf(3.36, 3.37, 3.52, 3.77, 4.07, 4.26, 4.62)

    printInterpolNPart(waveLengthAu, realPartAu, imaginaryPartAu, "AU")
    printInterpolEpsilon(waveLengthAu, realPartAu, imaginaryPartAu, "AU")
    printInterpolNPart(waveLengthAg, realPartAg, imaginaryPartAg, "AG")
    printInterpolEpsilon(waveLengthAg, realPartAg, imaginaryPartAg, "AG")
    printInterpolNPart(waveLengthCr, realPartCr, imaginaryPartCr, "Cr-1")
    printInterpolEpsilon(waveLengthCr, realPartCr, imaginaryPartCr, "Cr-1")
    printInterpolNPart(waveLengthCr2, realPartCr2, imaginaryPartCr2, "Cr-2")
    printInterpolEpsilon(waveLengthCr2, realPartCr2, imaginaryPartCr2, "Cr-2")
    printInterpolNPart(waveLengthFe, realPartFe, imaginaryPartFe, "Fe")
    printInterpolEpsilon(waveLengthFe, realPartFe, imaginaryPartFe, "Fe")
}