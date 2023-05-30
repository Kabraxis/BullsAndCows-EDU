package bullscows;

import java.util.Scanner;

public class Main {
    static int turn = 1;
    static final int indexBulls = 0;
    static final int indexCows = 1;
    static final int winBulls = 4;
    static String secretINP = "9305";

    public static void main(String[] args) {
        SecretCode newCode = new SecretCode();
        System.out.printf("\nThe random secret number is %s",
                newCode.getSecretCode(Integer.parseInt(getInput())));
    }

    protected static void run() {
        char[] secret = secretINP.toCharArray();
        // Getting input from a different method seems to prevent from unwanted prompts for input.
        String guessINP = getInput();
        char[] guess = guessINP.toCharArray();
        Grader grade = new Grader();
        int[] animalFold = grade.countCows(secret, guess);
        if (animalFold[indexBulls] == winBulls) {
            System.out.printf("Grade: %d bulls.\nCongrats! The secret code is %s.",
                    animalFold[indexBulls], secretINP);
        } else {
            if (animalFold[indexBulls] == 0 && animalFold[indexCows] == 0) {
                System.out.printf("Grade: None. The secret code is %s.\n",
                        secretINP);
                turn++;
                run();
            } else {
                // Decided to go for switch statement, for the sake of code clarity. Too may if-statements would
                // clutter code and make it harder to read.
                switch (animalFold[indexBulls]) {
                    case 0:
                        System.out.printf("Grade: %d cow(s). The secret code is %s.\n",
                                animalFold[indexCows], secretINP);
                        turn++;
                        run();
                        break;
                    default:
                        System.out.printf("Grade: %d bull(s) %d cow(s). The secret code is %s.\n",
                                animalFold[indexBulls], animalFold[indexCows], secretINP);
                        turn++;
                        run();
                        break;
                }
            }
        }
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
/*

- generate pseudo-random secret number of given length - user input must be smaller than 11.
- if length is greater than 10, print warning "Error: can't generate a secret number with a length of 11 because there
  aren't enough unique digits." and do not generate a number.
- use long pseudoRandomNumber = System.nanoTime(); to generate the numbers.
- You can generate a secret code by iterating over the pseudoRandomNumber in the reverse order and adding unique digits.
- If the pseudoRandomNumber lacks the required number of unique digits, call System.nanoTime() again and try to generate
  the secret code again until you get a satisfactory result.
- You can use the Character.getNumericValue(char a) method to get an integer representation of a number!
- code may contain any digits from 0 to 9 only once
- code shouldn't start with a digit 0
 */
