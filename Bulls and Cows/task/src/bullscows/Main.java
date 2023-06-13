package bullscows;

import java.util.Scanner;

/**
 * ~~Class description~~
 * Here the game gets initialized and executed. User input will be taken.
 * WARNING: There is no input safety check!
 * Wrong input, like letters instead of numbers will crash the application.
 * The user prompts are located in this class, as well as the determination if the user won the game or another turn is
 * required.
 * <p>
 * <p>
 * ~~Task description~~
 * In this stage, you should combine all the previous parts into a simple playable version of the "Bulls and Cows" game.
 * First, prompt the player to input the length of the secret code. The length will determine the difficulty level for
 * the current game session. The program should generate a secret code of the given length. Remember that it should
 * consist of unique numbers.
 * <p>
 * Then, the game starts and the program prompts the player to guess the code. When the player inputs a number, the
 * program should grade it in bulls and cows. The game goes on until the code is guessed, that is, until the number of
 * bulls is equal to the number of digits in the code. When the game ends, the program should finish its execution.
 * <p>
 * <p>
 * ~~Objective~~
 * In this stage, your program should:
 * 1. Ask for the length of the secret code and then generate the code.
 * 2. Wait for the user input.
 * 3. Grade the guessing attempt in bulls and cows.
 * 4. If the secret code has been guessed, the program stops; otherwise, return to the second step.
 * <p>
 * <p>
 * ~~Examples~~
 * Please, enter the secret code's length:
 * > 4
 * Okay, let's start a game!
 * Turn 1:
 * > 1234
 * Grade: 1 bull and 1 cow
 * Turn 2:
 * > 7354
 * Grade: 2 bulls and 1 cow
 * Turn 3:
 * > 9374
 * Grade: 4 bulls
 * Congratulations! You guessed the secret code.
 */

public class Main {
    // Game starts at turn 1
    static int turn = 1;

    // The amount of bulls and cows are stored in an array. These are the indices that describe where in animalFold[]
    // the corresponding values are stored.
    static final int indexBulls = 0;
    static final int indexCows = 1;

    // How many bulls are required to win is determined by the length of the code the user wants to guess.
    static int winBulls;

    static String secretCode;

    public static void main(String[] args) {
        initialize();
    }

    // Starts the game, by getting a new secret code and setting win condition.
    protected static void initialize() {
        System.out.println("Please, enter the secret code's length:");
        // Anonymous object, since it's only used here.
        // String input ins parsed to int, since SecretCode().getSecretCode() requires int, for
        // further detail: See getInput()
        secretCode = new SecretCode().getSecretCode(Integer.parseInt(getInput()));
        winBulls = secretCode.length();
        System.out.println("Okay, let's start the game!");

        run();
    }

    // Manages the game logic. Generated secret code and the users guess are pre-processed here, before Grader()
    // evaluates these against each other.
    // This method could have been broken down further, but I wanted to keep things simple.
    protected static void run() {
        System.out.printf("Turn %d: \n",
                turn);

        // Evaluation of secret code vs. guess is done by string processing, which I found easier to implement.
        char[] secret = secretCode.toCharArray();
        char[] guess = getInput().toCharArray();

        // Anonymous object, since it's only used here.
        // animalFold[] is where the code saves how many bulls and cows are correctly guesses.
        int[] animalFold = new Grader().countCows(secret, guess);

        if (animalFold[indexBulls] == winBulls) {
            System.out.printf("Grade: %d bulls.\nCongrats! The secret code is %s.",
                    animalFold[indexBulls], secretCode);
        } else {
            if (animalFold[indexBulls] == 0 && animalFold[indexCows] == 0) {
                System.out.println("Grade: None.");
                turn++;
                run();
            } else {
                // Decided to go for switch statement, for the sake of code clarity. Too may if-statements would
                // clutter code and make it harder to read.
                switch (animalFold[indexBulls]) {
                    case 0:
                        System.out.printf("Grade: %d cow(s).\n",
                                animalFold[indexCows]);
                        turn++;
                        run();
                        break;
                    default:
                        System.out.printf("Grade: %d bull(s) %d cow(s).\n",
                                animalFold[indexBulls], animalFold[indexCows]);
                        turn++;
                        run();
                        break;
                }
            }
        }
    }

    // I wanted to have only one method responsible to get user input. As the whole evaluation of the secret code vs.
    // guessed code is done by string processing, all inputs are taken as string.
    // WARNING: There is no input safety, in order to save time by focusing on the main task objectives.
    protected static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}