package com.lucadani;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;


/**
 * The Lobby class provides functionality to process a list of numeric strings,
 * either from an input file or default values, and compute results based on
 * specific numeric extraction rules. The main functionality includes reading
 * input, handling fallback defaults, and computing results with a specific
 * set of digits from the string data.
 */
public class Lobby {
    private static final List<String> DEFAULT_INPUT = Arrays.asList(
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111"
    );

    public static void main(String[] args) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("Lobby/input.txt"));
            if (lines.isEmpty()) {
                System.err.println("File 'input.txt' is empty. Using default input.");
                lines = DEFAULT_INPUT;
            }
        } catch (IOException e) {
            System.err.println("File 'input.txt' not found. Using default input.");
            lines = DEFAULT_INPUT;
        }
        System.out.println("--- Results ---");
        System.out.println("Part 1 (2 digits): " + solve(lines, 2));
        System.out.println("Part 2 (12 digits): " + solve(lines, 12));
    }

    /**
     * Computes the sum of the largest numbers that can be formed from a specified
     * number of digits in each string of the provided list. Each string represents
     * a "bank" of digits, and the method finds the highest possible number that
     * can be constructed using the specified number of digits, maintaining their
     * original order.
     *
     * @param banks     A list of strings where each string is a series of digits
     *                  representing a "bank".
     * @param numDigits The number of digits to extract and use for constructing
     *                  the largest possible number for each string.
     * @return          The sum of the largest numbers that can be formed from
     *                  the specified number of digits across all provided strings.
     */
    public static long solve(List<String> banks, int numDigits) {
        BiFunction<String, Integer, Long> findMaxNumber = (bank, k) -> {
            StringBuilder result = new StringBuilder();
            int searchStart = 0;
            for (int i = 0; i < k; i++) {
                int remainingNeeded = k - 1 - i;
                int searchEnd = bank.length() - remainingNeeded;
                int maxDigit = -1;
                int maxIndex = -1;
                for (int j = searchStart; j < searchEnd; j++) {
                    int currentDigit = bank.charAt(j) - '0';
                    if (currentDigit > maxDigit) {
                        maxDigit = currentDigit;
                        maxIndex = j;
                        if (maxDigit == 9) {
                            break; // Greedy optimization
                        }
                    }
                }
                result.append(maxDigit);
                searchStart = maxIndex + 1;
            }
            return Long.parseLong(result.toString());
        };

        long totalSum = 0;
        for (String bank : banks) {
            String trimmed = bank.trim();
            if (trimmed.length() < numDigits) {
                continue; // Skip lines too short
            }
            totalSum += findMaxNumber.apply(trimmed, numDigits);
        }
        return totalSum;
    }
}