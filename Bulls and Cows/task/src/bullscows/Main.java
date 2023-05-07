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
        String guessINP = getInput();
        char[] guess = guessINP.toCharArray();
        Grader grade = new Grader();
        int[] animalFold = grade.countCows(secret, guess);
        if (animalFold[indexBulls] == winBulls) {
            System.out.printf("Grade: %d bulls.\nCongrats! The secret code is %s.",
                    animalFold[indexBulls], secretINP);
        } else {
            // TODO: implement turn increase + message
            // TODO: need to fix the recursive call issue
            if (animalFold[indexBulls] == 0 && animalFold[indexCows] == 0) {
                System.out.printf("Grade: None. The secret code is %s.\n",
                        secretINP);
                turn++;
                run();
            } else {
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