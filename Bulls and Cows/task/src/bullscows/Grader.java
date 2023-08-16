package bullscows;

/**
 *  * ~~Class description~~
 * Secret code vs. guessed code comparison.
 */
class Grader {

    private int bulls;
    private int cows;

    // A bull is, if the guessed number is at the same position of the generated number.
    // Whereas a cow is, if the guessed number is part of the generated number, but only at the wrong place.
    protected void gradeGuess(String secretCode, String guess) {
        // Reset bulls and cows ever guess. This flushes results of previous turn.
        bulls = 0;
        cows = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            for (int j = 0; j < guess.length(); j++) {
                if (secretCode.charAt(i) == guess.charAt(j) && i == j) {
                    bulls++;
                }
                if (secretCode.charAt(i) == guess.charAt(j)) {
                    cows++;
                }
            }
        }

        // Prevent counting bulls as cows
        if (cows >= bulls) {
            cows -= bulls;
        }
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

}