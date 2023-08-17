package bullscows;

/**
 * ~~Class description~~
 * Secret code vs. guessed code comparison.
 */
class Grader {

    private int bulls;
    private int cows;

    // A bull is, if the guessed character is equal and at the same position as in the secret code.
    // Whereas a cow is, if the guessed character is part of the secret code, but only at the wrong place.
    protected void gradeGuess(String secretCode, String guess) {
        resetScoresToAvoidCarryover();
        StringBuilder wasBull = new StringBuilder();
        StringBuilder wasCow = new StringBuilder();

        // Bulls are checken
        for (int i = 0; i < secretCode.length(); i++) {
            for (int j = 0; j < guess.length(); j++) {
                if (secretCode.charAt(i) == guess.charAt(j) && i == j) {
                    wasBull.append(guess.charAt(j));
                    bulls++;
                }
            }
        }

        // Cows are checked, and count only if the character haven't been either a bull or cow before
        for (int i = 0; i < secretCode.length(); i++) {
            for (int j = 0; j < guess.length(); j++) {
                if (!String.valueOf(wasBull).contains(String.valueOf(guess.charAt(j))) &&
                        !String.valueOf(wasCow).contains(String.valueOf(guess.charAt(j))) &&
                        secretCode.charAt(i) == guess.charAt(j)) {
                    wasCow.append(guess.charAt(j));
                    cows++;
                }
            }
        }
    }

    private void resetScoresToAvoidCarryover() {
        bulls = 0;
        cows = 0;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

}