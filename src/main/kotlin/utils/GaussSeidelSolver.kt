package utils

import exceptions.ImpossibleToSolveException
import kotlin.math.abs
import kotlin.system.exitProcess

object GaussSeidelSolver {

    fun solve(matrix: MutableList<List<Double>>) {
        try{
            val m = checkDiagonalPredominance(matrix)
            println(m)
        }
        catch (e: ImpossibleToSolveException){
            println("Невозможно решить уравнение")
            exitProcess(0)
        }

    }

    private fun checkDiagonalPredominance(matrix: MutableList<List<Double>>): MutableList<List<Double>> {
        val map = HashMap<Int, List<Double>>()
        for(i in matrix.indices){
            for(j in 0..matrix[i].size - 2) {
                if (2 * abs(matrix[i][j]) > matrix[i].sumOf { abs(it) } - abs(matrix[i][matrix[i].lastIndex])) {
                    if (map[j] == null) {
                        map[j] = matrix[i];
                        break;
                    } else throw ImpossibleToSolveException()
                }
            }
        }
        if(map.size != matrix.size) throw ImpossibleToSolveException()
        return map.values.toMutableList();
    }




}