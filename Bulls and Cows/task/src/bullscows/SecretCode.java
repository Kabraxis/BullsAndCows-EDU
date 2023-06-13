package bullscows;


/**
 * ~~Class description~~
 * This class has the task of generating a new secret code of a user-given length, which than has to be guessed in the
 * Bulls and Cows game. In the process, System.nanoTime is utilized to generate a pseudo-random number as a string.
 * The secret code's length must not exceed a length of 10, since longer numbers would lack uniqueness.
 */

class SecretCode {
    private int codeGivenSize;

    // Create getSecretCode and createCode are separated from each other to maintain re-usability.
    // This way, adjustCodeLength can be utilized createCode to generate an additional secret code,
    // if the first one is too small.
    protected String getSecretCode(int codeGivenSize) {
        this.codeGivenSize = codeGivenSize;

        while (this.codeGivenSize > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %d because " +
                    "there aren't enough unique digits.\n", codeGivenSize);

            this.codeGivenSize = Integer.parseInt(Main.getInput());
        }

        StringBuilder randomCode = createCode();

        while (randomCode.length() != this.codeGivenSize) {
            adjustCodeLength(randomCode);
        }

        return randomCode.toString();
    }

    // Creates a new secret code which will be customized as the task requests.
    // Code length is not affected, so there are more digits to choose if a second code for spare numbers is needed.
    private StringBuilder createCode() {
        StringBuilder code = new StringBuilder(getPseudoRandomNumberString());

        reverseCode(code);
        deleteLeadingZero(code);
        deleteDuplicates(code);

        return code;
    }

    private void reverseCode(StringBuilder generatedCode) {
        generatedCode.reverse();
    }

    private void deleteLeadingZero(StringBuilder generatedCode) {
        while (generatedCode.charAt(0) == '0') {
            generatedCode.delete(0, 1);
        }
    }

    // Inner loop compares all the other characters to the character of the outer loop.
    // It runs backwards to prevent skipping indices after deletion.
    private void deleteDuplicates(StringBuilder generatedCode) {
        for (int i = 0; i < generatedCode.length(); i++) {
            for (int j = generatedCode.length() - 1; j > i; j--) {
                if (generatedCode.charAt(i) == generatedCode.charAt(j)) {
                    generatedCode.deleteCharAt(j);
                }
            }
        }
    }

    private void adjustCodeLength(StringBuilder generatedCode) {
        if (generatedCode.length() > codeGivenSize) {
            shortenCode(generatedCode);
        } else {
            extendCode(generatedCode);
        }
    }

    private void shortenCode(StringBuilder generatedCode) {
        generatedCode.delete(codeGivenSize, generatedCode.length());
    }

    private void extendCode(StringBuilder generatedCode) {
        StringBuilder spareRandomCode = createCode();
        generatedCode.append(spareRandomCode);
        deleteDuplicates(generatedCode);
    }

    private String getPseudoRandomNumberString() {
        return Long.toString(System.nanoTime());
    }

}