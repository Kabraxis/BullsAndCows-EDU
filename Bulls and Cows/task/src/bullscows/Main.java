package bullscows;

import java.util.Scanner;

public class Main {
    static int turn = 1;
    static final int indexBulls = 0;
    static final int indexCows = 1;
    static final int winBulls = 4;
    static String secretINP = "9305";

    public static void main(String[] args) {
        run();
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