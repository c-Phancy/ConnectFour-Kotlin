fun main() {
    val resetColor = "\u001B[0m"
    val player2Piece = "\u001B[96mO$resetColor"
    val player1Piece = "\u001B[31mX$resetColor"
    var win = "It's a stalemate!"
    var randomPick = emptyArray<Int>()
    for (col in 0 until gameBoard[0].size) {
        randomPick += col
    }
    var pickCol: Int
    var turn = 1
    printPrettyBoard(gameBoard)
    // PvC
    do {
        var pickedRow = 0
        var played = false
        if (turn % 2 != 0) {
            print("Select a column to place your piece ($player1Piece): ")
            do {
                pickCol = readLine()!!.toInt() - 1
                if (validateMove(pickCol).first) {
                    pickedRow = validateMove(pickCol).second
                    gameBoard[pickedRow][pickCol] = player1Piece
                    played = true
                } else {
                    print("Please pick a new column: ")
                }
                if (checkWin(pickedRow, pickCol, player1Piece)) {
                    win = "$player1Piece wins!"
                }
            } while (!played)
        } else {
            println("Computer($player2Piece) is thinking...")
//            TimeUnit.MILLISECONDS.sleep(800)
//            println("Computer ($player2Piece) has made its move.")
            do {
//                pickCol = randomPick.random()
                pickCol = readLine()!!.toInt() - 1
                if (validateMove(pickCol).first) {
                    pickedRow = validateMove(pickCol).second
                    gameBoard[pickedRow][pickCol] = player2Piece
                    played = true
                }
                if (checkWin(pickedRow, pickCol, player2Piece)) {
                    win = "$player2Piece wins!"
                }
            } while (!played)
        }
        printPrettyBoard(gameBoard)
        turn++
    } while (win != "It's a stalemate" ||
//            (!checkWin(pickedRow, pickCol, player1Piece) && !checkWin(pickedRow, pickCol, player2Piece) ||
                 turn <= gameBoard.size * gameBoard[0].size)
    println(win)
}