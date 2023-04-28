package bullscows;

public class Grader {
    private int bulls = 0;
    private int cows = 0;

    protected int[] countCows(char[] secret, char[] guess) {
        for (int i = 0; i < secret.length; i++) {
            for (int j = 0; j < guess.length; j++) {
                if (secret[i] == guess[j] && i == j) {
                    bulls++;
                }

                if (secret[i] == guess[j]) {
                    cows++;
                }
            }
        }
        return new int[]{bulls, cows >= bulls ? cows - bulls : cows};
    }

}