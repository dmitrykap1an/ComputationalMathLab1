import utils.CLI
import utils.GaussSeidelSolver

fun main(){
    val (matrix, acc) = CLI.askInputOption()
    GaussSeidelSolver.solve(acc!!, matrix)
}