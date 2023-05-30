package bullscows;


/**
 * ~~Class description~~
 * This class has the task of generating a new secret code of a user-given length, which than has to be guessed in the
 * Bulls and Cows game. In the process, System.nanoTime is utilized to generate a pseudo-random number as a string.
 * The secret code's length must not exceed a length of 10, since longer numbers would lack uniqueness of numbers.
 * The code written here is a pre-requirement for the further game development. For further detail, please read
 * the task description.
 * <p>
 * <p>
 * ~~Task description~~
 * Using a predefined secret code doesn't make a fun game. Let's implement the ability to generate a pseudo-random
 * secret number of a given length. If the length is greater than 10, print a warning message and do not
 * generate a number.
 * <p>
 * We suggest you use the following algorithm to generate the numbers:
 * long pseudoRandomNumber = System.nanoTime();
 * This code saves the nanoseconds elapsed since a certain time to the pseudoRandomNumber variable. We can assume
 * that this is a random number. You can generate a secret code by iterating over the pseudoRandomNumber in the
 * reverse order and adding unique digits. If the pseudoRandomNumber lacks the required number of unique digits,
 * call System.nanoTime() again and try to generate the secret code again until you get a satisfactory result.
 * You can use the Character.getNumericValue(char a) method to get an integer representation of a number!
 * <p>
 * Objective
 * In this stage, your program should generate a pseudo-random number of a given length with unique digits and print it.
 * If the length is greater than 10, the program should print a message containing the word Error. The secret code may
 * contain any digits from 0 to 9 but only once. The secret code shouldn't start with a digit 0: for the first digit of
 * the secret code, use digits from 1 to 9.
 * Don't delete your previous work, just move your code to a separate method. You will need it in the future stages.
 * <p>
 * Examples
 * The greater-than symbol followed by a space > represents the user input. Note that it's not part of the input.
 * Example 1
 * > 5
 * The random secret number is 48379.
 * <p>
 * Example 2
 * > 4
 * The random secret number is 5213.
 * <p>
 * Example 3
 * > 11
 * Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.
 * <p>
 */

public class SecretCode {
    private int codeGivenSize;

    // Create getSecretCode and createCode are separated from each other to maintain re-usability.
    // This way, adjustCodeLength can be utilized createCode to generate an additional secret code,
    // if the first one is too small.
    public String getSecretCode(int codeGivenSize) {
        this.codeGivenSize = codeGivenSize;

        while (this.codeGivenSize > 10) {
            System.out.printf("\nError: can't generate a secret number with a length of %d because " +
                    "there aren't enough unique digits.", codeGivenSize);

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

    public void reverseCode(StringBuilder generatedCode) {
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