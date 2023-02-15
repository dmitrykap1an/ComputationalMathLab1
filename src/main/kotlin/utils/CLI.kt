package utils

import exceptions.RowLengthException
import java.io.BufferedReader
import java.io.FileReader
import java.lang.NumberFormatException
import kotlin.system.exitProcess

object CLI {
    private var matrixSize: Int? = null
    private var matrix: MutableList<List<Double>> = mutableListOf()
    private val bw = BufferedReader(FileReader("src/matrix.txt"))
    private lateinit var input: () -> String
    private var visible = true
    private var accuracy: Double? = null

    fun askInputOption(): MutableList<List<Double>> {
            print("Прочитать данные из файла? Д/н ")
            val str = readln()
            input = when (str.uppercase()) {
                "Д", "\n" -> {
                    visible = false
                    { bw.readLine() }
                }

                else -> {
                    visible = true
                    { readln() }
                }
            }
            return askMatrix()
    }

    fun getMatrixSize() =
        matrixSize

    fun getAccuracy() =
        accuracy

    private fun askAccuracy(){
        ask("Введите точность: ")
        accuracy = input().toDouble()
    }

    private fun askMatrixSize() {
        ask("Введите размер матрицы: ")
        matrixSize = input().toInt()
    }

    private fun askMatrix(): MutableList<List<Double>> {
        try {
            askAccuracy()
            askMatrixSize()
            repeat(matrixSize!!) { numberOfRow ->
                ask("Введите ${numberOfRow + 1} строку матрицы: ")
                val row = input().split(" ").map { it.toDouble() }
                if (row.size != matrixSize!! + 1) throw RowLengthException()
                matrix.add(row)
            }
            return matrix
        } catch (e: RowLengthException) {
            println("Длина строки матрицы должна быть равна размеру матрицы!!")
            exitProcess(100);
        } catch (e: NumberFormatException) {
            println("Ошибка ввода данных")
            exitProcess(100)
        }
    }

    private fun ask(text: String){
        if(visible) print(text)
    }
}