import kotlin.random.Random

// Data Class (Stretch Requirement)
data class Player(var name: String, var score: Int = 0)

// Main Game Class
class HangmanGame(private val word: String, private val maxAttempts: Int) {

    private val guessedLetters = mutableSetOf<Char>() // Collection
    private var remainingAttempts = maxAttempts

    fun displayWord(): String {
        return word.map {
            if (guessedLetters.contains(it)) it else '_'
        }.joinToString(" ")
    }

    fun displayGuessedLetters(): String {
        return guessedLetters.joinToString(", ")
    }

    fun isWordGuessed(): Boolean {
        return word.all { guessedLetters.contains(it) }
    }

    fun guess(letter: Char, player: Player) {

        // when used for multiple logic branches
        when {
            guessedLetters.contains(letter) -> {
                println("⚠️ Already guessed '$letter'")
            }

            word.contains(letter) -> {
                println("✅ Correct!")
                guessedLetters.add(letter)
                player.score += 10
            }

            else -> {
                println("❌ Wrong!")
                guessedLetters.add(letter)
                remainingAttempts--
                player.score -= 2
            }
        }
    }

    fun getRemainingAttempts() = remainingAttempts
}

fun main() {

    println("🎮 Welcome to Hangman!")
    print("Enter your name: ")
    val playerName = readLine() ?: "Player"

    val player = Player(playerName) // Data class usage

    // Collection: categories (Map)
    val categories = mapOf(
        "tech" to listOf("kotlin", "android", "developer"),
        "animals" to listOf("tiger", "elephant", "giraffe")
    )

    println("\nChoose a category: tech / animals")
    val categoryChoice = readLine()?.lowercase()

    val wordList = categories[categoryChoice] ?: categories["tech"]!!
    val chosenWord = wordList[Random.nextInt(wordList.size)]

    println("Choose difficulty: easy / medium / hard")
    val difficulty = readLine()?.lowercase()

    // when used for value assignment
    val attempts = when (difficulty) {
        "easy" -> 8
        "hard" -> 4
        "medium" -> 6
        else -> 6
    }

    val game = HangmanGame(chosenWord, attempts)

    // Collection: score history
    val scoreHistory = mutableListOf<Int>()

    while (game.getRemainingAttempts() > 0 && !game.isWordGuessed()) {

        println("\n----------------------")
        println("Word: ${game.displayWord()}")
        println("Guessed: ${game.displayGuessedLetters()}")
        println("Attempts left: ${game.getRemainingAttempts()}")
        println("Score: ${player.score}")

        print("Enter a letter: ")
        val input = readLine()

        if (input.isNullOrEmpty()) {
            println("Please enter a valid letter.")
            continue
        }

        val letter = input[0].lowercaseChar()
        game.guess(letter, player)

        // Modify collection (add score after each turn)
        scoreHistory.add(player.score)
    }

    println("\n======================")

    if (game.isWordGuessed()) {
        println("🎉 YOU WIN, ${player.name}!")
        player.score += 20
    } else {
        println("💀 GAME OVER, ${player.name}!")
        println("The word was: $chosenWord")
    }

    println("Final Score: ${player.score}")

    // Display collection
    println("Score History: $scoreHistory")

    // when used for menu/replay logic
    println("\nPlay again? (yes/no)")
    val replay = readLine()?.lowercase()

    when (replay) {
        "yes", "y" -> println("Restart the program to play again!")
        "no", "n" -> println("Thanks for playing!")
        else -> println("Invalid input. Exiting game.")
    }
}
