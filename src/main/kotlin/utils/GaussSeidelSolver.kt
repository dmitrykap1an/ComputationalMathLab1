package utils

import exceptions.ImpossibleToSolveException
import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.math.max
import kotlin.system.exitProcess

object GaussSeidelSolver {

    fun solve(acc: Double, matrix: MutableList<List<Double>>) {
        try {
            val m = checkDiagonalPredominance(matrix)
            var approximation = getFirstApproximation(m)
            var cnt = 1;

            var newApproximation = getNewApproximation(m, approximation);
            while (checkEnd(approximation, newApproximation, acc)) {
                approximation = newApproximation;
                newApproximation = getNewApproximation(m, approximation)
                cnt++
            }

            print("Вектор неизвестных: ")
            printVector(newApproximation)
            println("Количество итераций, за которое было найдено решение: $cnt")
            print("Вектор погрешностей: ")
            printVector(getVectorOfInaccuracy(approximation, newApproximation))


        } catch (e: ImpossibleToSolveException) {
            println("Невозможно решить уравнение")
            exitProcess(0)
        }

    }

    private fun getNewApproximation(m: MutableList<List<Double>>, app: MutableList<Double>): MutableList<Double> {
        val newApproximation: MutableList<Double> = mutableListOf()
        for (i in m.indices) {
            var sum = m[i].last();
            for (j in 0 until i) {
                sum -= m[i][j] * newApproximation[j]
            }
            for (j in i + 1 until m.size) {
                sum -= m[i][j] * app[j]
            }
            newApproximation.add(sum / m[i][i])
        }
        return newApproximation
    }

    private fun printVector(vector: MutableList<Double>){
        val sb = StringBuilder().append("(")
        for(i in vector.indices){
            sb.append("x${i + 1} = ${vector[i]}")
            if(i != vector.lastIndex){
                sb.append(", ")
            }
        }
        println(sb.append(")").toString())
    }
    private fun getVectorOfInaccuracy(app: MutableList<Double>, newApp: MutableList<Double>): MutableList<Double> {
        val vectorOfInaccuracy = mutableListOf<Double>()
        for (i in app.indices) {
            vectorOfInaccuracy.add(abs(app[i] - newApp[i]))
        }
        return vectorOfInaccuracy
    }

    private fun checkEnd(app: MutableList<Double>, newApp: MutableList<Double>, accuracy: Double): Boolean {
        var max = 0.0;
        for (i in app.indices) {
            max = max(abs(app[i] - newApp[i]), max)
        }
        return max > accuracy;
    }

    private fun getFirstApproximation(m: MutableList<List<Double>>): MutableList<Double> {
        val firstApproximation: MutableList<Double> = mutableListOf()
        for (i in m.indices) {
            firstApproximation.add(m[i].last() / m[i][i])
        }
        return firstApproximation
    }

    private fun checkDiagonalPredominance(matrix: MutableList<List<Double>>): MutableList<List<Double>> {
        val map = HashMap<Int, List<Double>>()
        for (i in matrix.indices) {
            for (j in 0..matrix[i].size - 2) {
                if (2 * abs(matrix[i][j]) > matrix[i].sumOf { abs(it) } - abs(matrix[i][matrix[i].lastIndex])) {
                    if (map[j] == null) {
                        map[j] = matrix[i];
                        break;
                    } else throw ImpossibleToSolveException()
                }
            }
        }
        if (map.size != matrix.size) throw ImpossibleToSolveException()
        return map.values.toMutableList();
    }
}