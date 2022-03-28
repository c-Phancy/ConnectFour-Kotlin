import java.util.concurrent.TimeUnit

/************************************************************
 *  Name:         Christina Phan
 *  Date:         2021/11/20
 *  Assignment:   Final Project: Connect Four (Pretty)
 *  Class Number: CIS 282
 *  Description:  Create a program that runs a pretty game of Connect Four against an opponent using user input.
 ************************************************************/
var gameBoard = Array(8) { Array(8) { "." } }

fun main() {
    val resetColor = "\u001B[0m"
    val player2Piece = "\u001B[96mO$resetColor"
    val player1Piece = "\u001B[31mX$resetColor"
    val player = true
    val computer = false
    val choicesArray = arrayOf("Y", "N")
    var skip: String
    val options = arrayOf("Player vs. Player", "Player vs. Computer", "Computer vs. Computer", "Quit")
    val quit = options.size
    var choice = 0
    var winningPair: Pair<Boolean, Int>
    while (choice != quit) {
        choice = selectMenu(options)
        println()
        when (choice) {
            1 -> {
                println("Player ($player1Piece) is playing against Player ($player2Piece).")
                winningPair = playGame("pretty", player1Piece, player2Piece, player, player, "")
                when (winningPair.first) {
                    true -> {
                        if (winningPair.second % 2 == 0) {
                            println("Player 1 ($player1Piece) wins!")
                        } else {
                            println("Player 2 ($player2Piece) wins!")
                        }
                    }
                    false -> {
                        println("It's a stalemate!")
                    }
                }
            }
            2 -> {
                println("Player ($player1Piece) is playing against Computer ($player2Piece).")
                skip = optionsPrompt("Do you want to skip the delay during the computer's turn? (Y/N)", choicesArray)
                winningPair = playGame("pretty", player1Piece, player2Piece, player, computer, skip)
                when (winningPair.first) {
                    true -> {
                        if (winningPair.second % 2 == 0) {
                            println("Player 1 ($player1Piece) wins!")
                        } else {
                            println("Computer ($player2Piece) wins!")
                        }
                    }
                    false -> {
                        println("It's a stalemate!")
                    }
                }
            }
            3 -> {
                println("Computer ($player1Piece) is playing against Computer ($player2Piece).")
                skip = optionsPrompt("Do you want to skip the delay during the computer's turn? (Y/N)", choicesArray)
                winningPair = playGame("pretty", player1Piece, player2Piece, computer, computer, skip)
                when (winningPair.first) {
                    true -> {
                        if (winningPair.second % 2 == 0) {
                            println("Computer 1 ($player1Piece) wins!")
                        } else {
                            println("Computer 2 ($player2Piece) wins!")
                        }
                    }
                    false -> {
                        println("It's a stalemate!")
                    }
                }
            }
            4 -> {
                println("See you next time!")
            }
            else -> {
                println("Please try a different option.")
            }
        }
        if (choice in 1 until quit) {
            clearBoard(gameBoard)
            println()
            println()
            println("Game over! What do you want to do next?")
            println()
        }
    }
}

fun selectMenu(options: Array<String>): Int {
    var choice: Int?
    for (option in options) {
        println("${options.indexOf(option) + 1}. $option")
    }
    do {
        print("Please enter your selection: ")
        choice = readLine()!!.toIntOrNull()
        if (choice == null) {
            println("Please try again.")
        }
    } while (choice == null)
    return choice
}

fun optionsPrompt(question: String, choices: Array<String>): String {
    var userInput: String
    do {
        print("$question ")
        userInput = readLine()!!.toString().uppercase()
        if (!choices.contains(userInput)) {
            println("Please try again.")
        }
        println()
    } while (!choices.contains(userInput))
    return userInput
}

fun printBoard(gameBoard: Array<Array<String>>) {
    for (col in gameBoard.indices) {
        print(col + 1)
        if (col != gameBoard.size - 1) {
            print(" ")
        }
    }
    println()
    for (row in gameBoard.indices) {
        for (col in 0 until gameBoard[row].size) {
            print(gameBoard[row][col])
            if (col != gameBoard[row].size - 1) {
                print(" ")
            }
        }
        println()
    }
}

fun clearBoard(gameBoard: Array<Array<String>>) {
    for (row in gameBoard.indices) {
        for (col in 0 until gameBoard[row].size) {
            gameBoard[row][col] = "."
        }
    }
}

fun printPrettyBoard(gameBoard: Array<Array<String>>) {
    val cross = 9580.toChar()
    val down = 9574.toChar()
    val up = 9577.toChar()
    val vertical = 9553.toChar()
    val horizontal = 9552.toChar().toString().repeat(3)

    // Header
    // Top
    print(9556.toChar())
    for (col in 1 until gameBoard[0].size) {
        print(horizontal + down)
    }
    println(horizontal + 9559.toChar())
    // Numbers
    for (col in 1..gameBoard[0].size) {
        print("$vertical $col ")
    }
    println(vertical)
    // Bottom
    print(9568.toChar())
    for (col in 1 until gameBoard[0].size) {
        print(horizontal + cross)
    }
    println(horizontal + 9571.toChar())

    // Middle
    for (row in gameBoard.indices) {
        for (col in 0 until gameBoard[row].size) {
            print(vertical + " ${gameBoard[row][col]} ")
        }
        println(vertical)
    }

    // Bottom
    print(9562.toChar())
    for (col in 1 until gameBoard[0].size) {
        print(horizontal + up)
    }
    println(horizontal + 9565.toChar())
}

fun checkHorizontal(row: Int, ch: String): Pair<Boolean, List<Pair<Int, Int>>> {
    var retBoolean = false
    var charCounter = 0
    var col = 0
    var largestCharCounter = 0
    var reset = false
    val winningHorizontal = mutableListOf<Pair<Int, Int>>()
    while (col in gameBoard[0].indices) {
        if (gameBoard[row][col] == ch) {
            charCounter++
            if (!reset)
                winningHorizontal += Pair(row, col)
        } else {
            if (largestCharCounter >= 4) {
                reset = true
            } else {
                winningHorizontal.clear()
            }
            charCounter = 0
        }
        if (charCounter >= 4) {
            retBoolean = true
            largestCharCounter = charCounter
        }
        col++
    }
    if (largestCharCounter < 4) {
        winningHorizontal.clear()
    }
    return Pair(retBoolean, winningHorizontal)
}

fun checkVertical(col: Int, ch: String): Pair<Boolean, List<Pair<Int, Int>>> {
    var retBoolean = false
    var charCounter = 0
    var row = gameBoard.indices.last
    val winningVertical = mutableListOf<Pair<Int, Int>>()
    while (row in gameBoard.indices && charCounter != 4) {
        if (gameBoard[row][col] == ch) {
            charCounter++
            winningVertical += Pair(row, col)
        } else {
            winningVertical.clear()
            charCounter = 0
        }
        row--
        if (charCounter == 4) {
            retBoolean = true
        }
    }
    if (winningVertical.size < 4) {
        winningVertical.clear()
    }
    return Pair(retBoolean, winningVertical)
}

fun checkDiagonal(row: Int, col: Int, ch: String): Pair<Boolean, List<Pair<Int, Int>>> {
    var outerRetBoolean: Boolean
    val winningList = mutableListOf<Pair<Int, Int>>()
    fun diagonal(direction: String): Pair<Boolean, List<Pair<Int, Int>>> {
        var retBoolean = false
        var currentRow = row
        var currentCol = col
        var charCounter = 0
        var largestCharCounter = 0
        var reset = false
        val winningDiagonal = mutableListOf<Pair<Int, Int>>()
        when (direction) {
            "SE" -> {
                if (row != 0 && col != 0) {
                    currentRow--
                    currentCol--
                }
                if (row != 0 && currentCol != 0) {
                    currentRow--
                    currentCol--
                }
            }
            "SW" -> {
                if (row != 0 && col != gameBoard[0].indices.last) {
                    currentRow--
                    currentCol++
                }
                if (currentRow != 0 && currentCol != gameBoard[0].indices.last) {
                    currentRow--
                    currentCol++
                }
            }
            "NE" -> {
                if (row != gameBoard.indices.last && col != 0) {
                    currentRow++
                    currentCol--
                }
                if (currentRow != gameBoard.indices.last && currentCol != 0) {
                    currentRow++
                    currentCol--
                }
            }
            "NW" -> {
                if (row != gameBoard.indices.last && col != gameBoard[0].indices.last) {
                    currentRow++
                    currentCol++
                }
                if (currentRow != gameBoard.indices.last && currentCol != gameBoard[0].indices.last) {
                    currentRow++
                    currentCol++
                }
            }
        }   // Checks the ends of the diagonal for when the winning piece is placed in the middle of the winning diagonal
        while (currentRow in gameBoard.indices &&
            currentCol in gameBoard[0].indices
        ) {
            if (gameBoard[currentRow][currentCol] == ch) {
                charCounter++
                if (!reset) {
                    winningDiagonal += Pair(currentRow, currentCol)
                }
            } else {
                charCounter = 0
                if (largestCharCounter >= 4) {
                    reset = true
                } else {
                    winningDiagonal.clear()
                }
            }
            if (charCounter >= 4) {
                retBoolean = true
                largestCharCounter = charCounter
            }
            when (direction) {
                "SE" -> {
                    currentRow++
                    currentCol++
                }
                "SW" -> {
                    currentRow++
                    currentCol--
                }
                "NE" -> {
                    currentRow--
                    currentCol++
                }
                "NW" -> {
                    currentRow--
                    currentCol--
                }
            }
        }
        if (largestCharCounter < 4) {
            winningDiagonal.clear()
        }
        return Pair(retBoolean, winningDiagonal)
    }

    var diagonal = diagonal("SE")
    outerRetBoolean = diagonal.first
    winningList += diagonal.second
    diagonal = diagonal("SW")
    if (diagonal.first) {
        outerRetBoolean = true
        winningList += diagonal.second
    }
    diagonal = diagonal("NE")
    if (diagonal.first) {
        outerRetBoolean = true
        winningList += diagonal.second
    }
    diagonal = diagonal("NW")
    if (diagonal.first) {
        outerRetBoolean = true
        winningList += diagonal.second
    }
    return Pair(outerRetBoolean, winningList)
}

fun checkWin(row: Int, col: Int, ch: String): Boolean {
    var retBoolean = false
    val winningPiece = if (ch == "\u001B[96mO\u001B[0m") {
        "\u001B[93mO\u001B[0m"
    } else {
        "\u001B[93mX\u001B[0m"
    }
    val allWinning = mutableListOf<Pair<Int, Int>>()
    if (checkHorizontal(row, ch).first || checkVertical(col, ch).first || checkDiagonal(row, col, ch).first) {
        retBoolean = true
        for (pair in checkHorizontal(row, ch).second) {
            allWinning += pair
        }
        for (pair in checkVertical(col, ch).second) {
            allWinning += pair
        }
        for (pair in checkDiagonal(row, col, ch).second) {
            allWinning += pair
        }
        for (pair in allWinning) {
            gameBoard[pair.first][pair.second] = winningPiece
        }
    }
    return retBoolean
}

fun validateMove(col: Int): Pair<Boolean, Int> {
    var pickedRow = -1
    var retBoolean = false
    if (col in gameBoard[0].indices) {
        for (row in gameBoard.indices) {
            if (gameBoard[row][col].contains(".")) {
                pickedRow++
                retBoolean = true
            }
        }
    }
    return Pair(retBoolean, pickedRow)
}

fun playerMove(player: String): Boolean {
    var pickCol: Int
    var pickedRow = 0
    var played = false
    do {
        do {
            print("Select a column to place your piece ($player): ")
            pickCol = try {
                readLine()!!.toInt() - 1
            } catch (e: NumberFormatException) {
                -1
            }
        } while (pickCol == -1)
        if (validateMove(pickCol).first) {
            pickedRow = validateMove(pickCol).second
            gameBoard[pickedRow][pickCol] = player
            played = true
        } else {
            print("Please pick a new column: ")
        }
    } while (!played)
    return checkWin(pickedRow, pickCol, player)
}

fun computerMove(player: String, skip: String): Boolean {
    var pickCol: Int
    var pickedRow = 0
    var played = false
    var randomPick = emptyArray<Int>()
    for (col in 0 until gameBoard[0].size) {
        randomPick += col
    }
    do {
        pickCol = randomPick.random()
        if (validateMove(pickCol).first) {
            pickedRow = validateMove(pickCol).second
            gameBoard[pickedRow][pickCol] = player
            if (skip == "N") {
                println("Computer ($player) is thinking...")
                TimeUnit.MILLISECONDS.sleep(1100)
            }
            println("Computer ($player) has made its move.")
            played = true
        }
    } while (!played)
    return (checkWin(pickedRow, pickCol, player))
}

fun playGame(
    boardType: String,
    player1Piece: String,
    player2Piece: String,
    player1: Boolean,
    player2: Boolean,
    skip: String,
): Pair<Boolean, Int> {
    var win: Boolean
    val winner: Pair<Boolean, Int>
    var turn = 1
    if (boardType == "pretty") {
        printPrettyBoard(gameBoard)
    } else {
        printBoard(gameBoard)
    }
    do {
        win = if (turn % 2 != 0) {
            if (player1) {
                (playerMove(player1Piece))
            } else {
                computerMove(player1Piece, skip)
            }
        } else {
            if (player2) {
                playerMove(player2Piece)
            } else {
                computerMove(player2Piece, skip)
            }
        }
        println()
        if (boardType == "pretty") {
            printPrettyBoard(gameBoard)
        } else {
            printBoard(gameBoard)
        }
        turn++
    } while (!win && turn <= gameBoard.size * gameBoard[0].size)
    winner = Pair(win, turn)
    return winner
}