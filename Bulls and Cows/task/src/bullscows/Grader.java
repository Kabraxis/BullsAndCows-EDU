package bullscows;

/**
 * Secret code vs. guessed code comparison is the only task for this class.
 */
class Grader {
    private int bulls = 0;
    private int cows = 0;

    // A bull is, if the guessed number is at the same position of the generated number.
    // Whereas a cow is, if the guessed number is part of the generated number, but only at the wrong place.
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

        // Returns a new array, index 0 equals to bulls, index 1 equals to cows detected in user's guess.
        // The evaluation for cows is: if cows >= bulls, then the value of cows is return cows - bulls, else return
        // the value of cows directly.
        // This is required, so bulls won't get count as cows.
        return new int[]{bulls, cows >= bulls ? cows - bulls : cows};
    }

}