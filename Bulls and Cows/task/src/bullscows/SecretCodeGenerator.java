package bullscows;

import java.util.Random;

/**
 * ~~Class description~~
 * This class has the task of generating a new secret code of a user-given length, which than has to be guessed in the
 * Bulls and Cows game.
 */

class SecretCodeGenerator {
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
            assureCodeLength(randomCode);
        }
        return randomCode.toString();
    }

    // Creates a new secret code of codeGivenLength, and only contains unique numbers.
    // Code length is not affected, so there are more digits to choose if a second code for spare numbers is needed.
    private StringBuilder createCode() {
        StringBuilder code = new StringBuilder(getPseudoRandomNumberString());
        deleteDuplicates(code);
        return code;
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

    private void assureCodeLength(StringBuilder generatedCode) {
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
        int minValue = (int) Math.pow(10, this.codeGivenSize - 1); // Calculate maximum value based on codeGivenSize.
        int maxValue = (int) Math.pow(10, this.codeGivenSize) - 1; // Calculate minimum value based on codeGivenSize.
        Random random = new Random();
        int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue; // Generate random number in range
        System.out.println(randomNumber);
        return String.valueOf(randomNumber);
    }

}
