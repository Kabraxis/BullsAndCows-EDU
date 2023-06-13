import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

/**
 * ~~Class description~~
 * This class takes four numbers as input from the user:
 * Start seed (A*), last seed (B*), numbers to generate (N*), range (exclusive) (K*) to generate.
 * The seeds are used to generate random numbers, from 0 - K (exclusive). The maximum of this sequence is saved, and
 * compared afterward with the maximum of the other sequences.
 * Hereafter, the code finds the lowest (minimum) value of all maximum values, and prints it with its
 * corresponding seed.
 * <p>
 * Footnotes marked by (*), please see Task description at the document's end.
 */

public class Main {
    public static void main(String[] args) {
        // Scanner is used to get the input numbers as a string, and is than processed further by another instance of
        // Scanner. This is a sleeker alternative approach than using .split() and going the "array-way".
        // Please note: variable names start with the letters from the task description for a better comprehensibility.
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner = new Scanner(input);
        int a_SeedStart = scanner.nextInt();
        int b_SeedEnd = scanner.nextInt();
        int totalSeeds = (b_SeedEnd - a_SeedStart) + 1;
        int n_Numbers = scanner.nextInt();
        int k_Range = scanner.nextInt();
        Random rnd;

        // All seeds are in this array, so the index of the lowest maximum can be better traced to its seed.
        int[] seeds = new int[totalSeeds];

        // Temporarily stores the sequences, in order to sort and extract the maximum number from it.
        int[] sequence = new int[n_Numbers];

        // The maximum value of each sequence are stored here, to be compared to the others.
        int[] maxInSequence = new int[totalSeeds];

        // Populating seeds[]
        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = a_SeedStart;
            a_SeedStart++;
        }

        // Populating sequence[] with random numbers generated using seeds[], from 0 to k_Range (excluding)
        for (int i = 0; i < seeds.length; i++) {
            rnd = new Random(seeds[i]);
            for (int j = 0; j < sequence.length; j++) {
                sequence[j] = rnd.nextInt(k_Range);

            }
            Arrays.sort(sequence);
            // .sort() works in ascending order
            maxInSequence[i] = sequence[sequence.length - 1];
        }

        // Determine the smallest number and its corresponding index
        int smallestValue = maxInSequence[0];
        int smallestIndex = 0;
        for (int i = 0; i < maxInSequence.length; i++) {
            if (maxInSequence[i] < smallestValue) {
                smallestValue = maxInSequence[i];
                smallestIndex = i;
            }
        }

        System.out.println(seeds[smallestIndex]);
        System.out.println(smallestValue);

    }
}
