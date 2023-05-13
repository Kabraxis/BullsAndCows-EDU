import java.util.Scanner;

class EvenUpperCase {

    public static String upperEvenLetters(String message) {
        StringBuilder evenIndexUppercase = new StringBuilder(message);
        for (int i = 0; i < evenIndexUppercase.length(); i++) {
            if (i % 2 == 0) {
                evenIndexUppercase.setCharAt(i, Character.toUpperCase(evenIndexUppercase.charAt(i)));
            }
        }
        return evenIndexUppercase.toString();
    }

    // Don't change the code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.next();

        System.out.println(upperEvenLetters(message));
    }
}