package bullscows;

import java.util.Random;

/**
 * ~~Class description~~
 * This class has the task of generating a new secret code of a user-given length, which than has to be guessed in the
 * Bulls and Cows game.
 */
class SecretCodeGenerator {

    // Set of possible characters
    private final char[] alphanumericArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public String getSecretCode(int codeLength, int codeComplexity) {
        // The complexity of the code should never be below 10, otherwise the algorithm will crash
        if (codeComplexity < 10) {
            codeComplexity = 10;
        }

        StringBuilder secretCode = new StringBuilder();
        secretCode.append(alphanumericArray[getRandomIntFromSeed(codeComplexity)]);

        while (secretCode.length() != codeLength) {
            int index = getRandomIntFromSeed(codeComplexity);
            if (secretCode.indexOf(String.valueOf(alphanumericArray[index])) == -1) {
                secretCode.append(alphanumericArray[index]);
            }
        }
        return String.valueOf(secretCode);
    }

    // Result excludes upper boundary/seed value itself
    public int getRandomIntFromSeed(int seed) {
        return new Random().nextInt(seed);
    }

    public char[] getAlphanumericArray() {
        char[] alphanumericCopy = new char[alphanumericArray.length];
        System.arraycopy(alphanumericArray, 0, alphanumericCopy, 0, alphanumericCopy.length);
        return alphanumericCopy;
    }

}