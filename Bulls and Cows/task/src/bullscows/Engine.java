package bullscows;

import java.util.Scanner;

/**
 * ~~Class description~~
 * The game gets initialized and executed here. User input will be taken.
 * WARNING: There is no input safety check!
 * Wrong input, like letters instead of numbers will likely crash the application.
 * The user prompts are located in this class, as well as the determination if the user won the game or another turn is
 * required.
 * <p>
 * <p>
 * ~~Task description~~
 * Some players need a challenge, so let's make the secret code in the game harder to guess. Add support for more than 10
 * symbols by adding letters. Now, the secret code can contain the numbers 0-9 and the lowercase Latin characters a-z.
 * The new maximum length for the code is 36. Note that the length of the secret word may not match the number of possible
 * characters in the secret code, so you should request input twice: once for the secret code length and once for the
 * number of possible characters.
 * <p>
 * ~~Objectives~~
 * In this step, your program should:
 * <p>
 * 1. Ask for the length of the secret code.
 * 2. Ask for the range of possible characters in the secret code.
 * 3. Generate a secret code using numbers and characters. This time, you should also print the secret code using *
 * characters and print, which characters were used to generate the secret code.
 * 4. Function as a fully playable game.
 * <p>
 * <p>
 * ~~Example~~
 * The greater-than symbol followed by a space > represents the user input. Note that it's not part of the input.
 * <p>
 * Input the length of the secret code:
 * > 4
 * Input the number of possible symbols in the code:
 * > 16
 * The secret is prepared: **** (0-9, a-f).
 * Okay, let's start a game!
 * Turn 1:
 * > 1a34
 * Grade: 1 bull and 1 cow
 * Turn 2:
 * > b354
 * Grade: 2 bulls and 1 cow
 * Turn 3:
 * > 93b4
 * Grade: 4 bulls
 * Congratulations! You guessed the secret code.
 */
public class Engine {
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

        int length = Integer.parseInt(getInput());

        while (length > 36) {
            System.out.println("Error: can't generate a secret with a length above 36 because there aren't enough" +
                    " unique characters.");
            System.out.println("Please enter a number not greater than 36.");
            System.out.println("Input the number of possible symbols in the code:");
            length = Integer.parseInt(getInput());
        }

        return length;
    }

    private int askCodeComplexity() {
        System.out.println("Input the number of possible symbols in the code:");

        int complexity = Integer.parseInt(getInput());

        while (complexity > 36) {
            System.out.println("Error: can't generate a secret with a length above 36 because there aren't enough" +
                    " unique characters.");
            System.out.println("Please enter a number not greater than 36.");
            System.out.println("Input the number of possible symbols in the code:");
            complexity = Integer.parseInt(getInput());
        }

        return complexity;
    }

    private void gameLoop() {
        Grader grader = new Grader();
        String guess;

        while (true) {
            System.out.printf("Turn %d: \n",
                    turn);
            guess = getInput();
            grader.gradeGuess(secretCode, guess);

            int bulls = grader.getBulls();
            int cows = grader.getCows();

            if (bulls == codeLength) {
                System.out.printf("Grade: %d bulls.\nCongratulations! You guessed the secret code.", codeLength);
                return;
            }

            StringBuilder gradeMsg = getStringBuilder(bulls, cows);

            System.out.println("Grade: " + gradeMsg + ".");
            turn++;
        }
    }

    // Determine the grading message based on the number of bulls and cows.
    private static StringBuilder getStringBuilder(int bulls, int cows) {
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

    private String getInput() {
        return new Scanner(System.in).nextLine();
    }

}