import java.util.concurrent.TimeUnit

fun main() {
    val resetColor = "\u001B[0m"
    val player2Piece = "\u001B[96mO$resetColor"
    val player1Piece = "\u001B[31mX$resetColor"
    printPrettyBoard(gameBoard)
    // PvC
//    do {
//        if (turn % 2 != 0) {
//            win = playerMove(player1Piece)
//        } else {
//            win = computerMove(player2Piece)
//        }
//        printPrettyBoard(gameBoard)
//        turn++
//    } while (!win && turn <= gameBoard.size * gameBoard[0].size)
//    println(win)
}