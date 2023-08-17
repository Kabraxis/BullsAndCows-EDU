package bullscows;

import java.util.Scanner;

/**
 * ~~Class description~~
 * <p>
 * The game gets initialized and executed here. User input will be taken.
 * WARNING: There is no input safety check!
 * Wrong input, like letters instead of numbers will likely crash the application.
 * The user prompts are located in this class, as well as the determination if the user won the game or another turn is
 * required.
 * <p>
 * <p>
 * ~~Task description~~
 * <p>
 * There are a lot of error possibilities. What if someone enters an answer of the wrong length? Or the number of
 * possible symbols is less than the length of the code? What if the answer contains invalid symbols? The game may crash
 * before the secret number was guessed!
 * <p>
 * Let's handle errors like this. At this point, you can implement this without the try catch construction. Use the
 * following rule of thumb: if you can avoid the exception-based logic, avoid it! If you use exceptions in normal
 * situations, how would you deal with unusual (exceptional) situations? Now it may not seem that important, but when
 * you need to find errors in more complex programs, this makes a difference.
 * <p>
 * ~~Objectives~~
 * <p>
 * In this stage, your program should:
 * <p>
 * 1. Handle incorrect input.
 * 2.Print an error message that contains the word error. After that, don't ask for the numbers again, just finish the
 * program.
 * <p>
 * ~~Example~~
 * <p>
 * The greater-than symbol followed by a space > represents the user input. Note that it's not part of the input.
 * <p>
 * Example 1
 * <p>
 * Input the length of the secret code:
 * > 6
 * Input the number of possible symbols in the code:
 * > 5
 * Error: it's not possible to generate a code with a length of 6 with 5 unique symbols.
 * <p>
 * Example 2
 * <p>
 * Input the length of the secret code:
 * > abc 0 -7
 * Error: "abc 0 -7" isn't a valid number.
 * <p>
 * Example 3
 * <p>
 * Input the length of the secret code:
 * > 6
 * Input the number of possible symbols in the code:
 * > 37
 * Error: maximum number of possible symbols in the code is 36 (0-9, a-z).
 * <p>
 * Example 4
 * <p>
 * Input the length of the secret code:
 * > 4
 * Input the number of possible symbols in the code:
 * > 12
 * The secret is prepared: **** (0-9, a-b).
 * Okay, let's start a game!
 * Turn 1:
 * > a234
 * Grade: 1 bull and 1 cow
 * Turn 2:
 * > 73b4
 * Grade: 2 bulls and 1 cow
 * Turn 3:
 * > 9374
 * Grade: 4 bulls
 * Congratulations! You guessed the secret code.
 */
public class Engine {
    final int MIN_CODE_LENGTH = 1;
    final int MAX_SYMBOLS = 36;
    final String VALID_SYMBOLS = "^[a-z0-9]+$";
    final String NUMBERS_ONLY = "\\d+";
    int turn = 1;
    int codeLength;
    int codeComplexity;
    String secretCode;
    SecretCodeGenerator generator;

    Engine() {
        generator = new SecretCodeGenerator();
    }

    void startGame() {
        codeLength = askCodeLength();
        codeComplexity = askCodeComplexity();
        secretCode = generator.getSecretCode(codeLength, codeComplexity);
        System.out.print("The secret code is prepared: ");

        for (int i = 0; i < codeLength; i++) {
            System.out.print("*");
        }

        System.out.println(codeComplexity < 11 ? " (0-9)" : " (0-9, " + generator.getAlphanumericArray()[10] + "-" +
                generator.getAlphanumericArray()[codeComplexity - 1] + ").");
        System.out.println("Okay, let's start a game!");

        gameLoop();
    }

    private int askCodeLength() {
        System.out.println("Input the length of the secret code:");
        String input = askInputReturnString();
        exitForInputNotNumbers(input);
        int length = Integer.parseInt(input);
        exitForCodeLengthBelowMinimum(length);
        exitForCodeLengthExceedMaxSymbols(length);
        return length;
    }

    private int askCodeComplexity() {
        System.out.println("Input the number of possible symbols in the code:");
        String input = askInputReturnString();
        exitForInputNotNumbers(input);
        int complexity = Integer.parseInt(input);
        exitForCodeComplexityNotEqualCodeLength(complexity);
        exitForCodeLengthExceedMaxSymbols(complexity);
        return complexity;
    }

    private void gameLoop() {
        Grader grader = new Grader();
        String guess;

        while (true) {
            System.out.printf("Turn %d: \n",
                    turn);
            guess = askInputReturnString();
            exitForGuessContainingInvalidSymbols(guess);
            exitForGuessLengthNotEqualCodeLength(guess);

            grader.gradeGuess(secretCode, guess);

            int bulls = grader.getBulls();
            int cows = grader.getCows();

            if (bulls == codeLength) {
                System.out.printf("Grade: %d bulls.\nCongratulations! You guessed the secret code.", codeLength);
                return;
            }

            StringBuilder gradeMsg = getStringBuilder(bulls, cows);

            System.out.println("Grade: " + gradeMsg);
            turn++;
        }
    }

    // Determine the grading message based on the number of bulls and cows.
    private StringBuilder getStringBuilder(int bulls, int cows) {
        StringBuilder gradeMsg = new StringBuilder();

        if (bulls > 0) {
            if (bulls == 1) {
                gradeMsg.append(String.format("%d bull", bulls));
            } else {
                gradeMsg.append(String.format("%d bulls", bulls));
            }
        }
        if (cows > 0) {
            if (!gradeMsg.isEmpty()) {
                gradeMsg.append(" "); // Add space if we already have bulls
            }
            if (cows == 1) {
                gradeMsg.append(String.format("%d cow", cows));
            } else {
                gradeMsg.append(String.format("%d cows", cows));
            }
        }
        if (gradeMsg.isEmpty()) {
            gradeMsg.append("None");
        }
        return gradeMsg;
    }

    private String askInputReturnString() {
        return new Scanner(System.in).nextLine();
    }

    // Warning: Danger Zone!
    // These methods are responsible for the conditions under which the program should terminate.
    private void exitForGuessLengthNotEqualCodeLength(String guess) {
        if (guess.length() != codeLength) {
            System.out.println("Error: guess must have the same amount of characters as the secret code.");
        }
    }

    private void exitForCodeComplexityNotEqualCodeLength(int complexity) {
        if (complexity < codeLength) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.",
                    codeLength, complexity);
            System.exit(0);
        }
    }

    private void exitForCodeLengthExceedMaxSymbols(int length) {
        if (length > MAX_SYMBOLS) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
    }

    private void exitForGuessContainingInvalidSymbols(String guess) {
        if (!guess.matches(VALID_SYMBOLS)) {
            System.out.printf("Error: \"%s\" contains invalid characters (0-9, a-z).", guess);
            System.exit(0);
        }
    }

    private void exitForInputNotNumbers(String input) {
        if (!input.matches(NUMBERS_ONLY)) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            System.exit(0);
        }
    }

    private void exitForCodeLengthBelowMinimum(int length) {
        if (length < MIN_CODE_LENGTH) {
            System.out.println("Error: code length must be at least 1");
            System.exit(0);
        }
    }

}