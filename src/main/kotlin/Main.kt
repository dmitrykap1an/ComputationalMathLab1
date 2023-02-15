import utils.CLI
import utils.GaussSeidelSolver

fun main(){
    val matrix = CLI.askInputOption()
    GaussSeidelSolver.solve(matrix)
}