package com.lucadani;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;


/**
 * The GiftShop class provides methods for solving a problem involving
 * analyzing ranges of numbers to compute specific sums based on defined
 * invalidation rules for numeric IDs.
 * <p>
 * The primary functionality of the class includes:
 * - Reading input data from a file or using a default dataset.
 * - Identifying invalid numeric IDs in specified ranges based on predefined
 *   rules.
 * - Calculating and returning sums of IDs deemed invalid under each rule.
 * <p>
 * The two invalidation rules are:
 * - Rule 1: A number is invalid if its string representation can be split
 *   exactly in half and both halves are identical.
 * - Rule 2: A number is invalid if its string representation can be completely
 *   composed of repeated patterns of a substring.
 * <p>
 * Methods:
 * - main: Entry point of the program that processes input, computes sums, and
 *   displays results.
 * - getDataToProcess: Reads input data from a file and provides fallback to
 *   default input if the file is missing or empty.
 * - solve: Processes input ranges, applies invalidation rules, and computes
 *   sums of IDs that match the rules.
 */
public class GiftShop {
    private static final String DEFAULT_INPUT = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
            + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
            + "824824821-824824827,2121212118-2121212124";


    public static void main(String[] args) throws IOException {
        String fileName = "GiftShop/input.txt";
        String dataToProcess = getDataToProcess(fileName);
        long[] results = solve(dataToProcess);
        System.out.printf("Part 1: sum of invalid IDs is %d\n", results[0]);
        System.out.printf("Part 2: sum of invalid IDs is %d\n", results[1]);
    }

    private static String getDataToProcess(String fileName) throws IOException {
        String fullPath = Paths.get(fileName).toAbsolutePath().toString();
        var path = Path.of(fullPath);
        if (!Files.exists(path)) {
            System.out.printf("The file %s does not exist, so we will use the default input.\n", fileName);
            return DEFAULT_INPUT;
        }
        String fileContent = Files.readString(path);
        if (fileContent.trim().isEmpty()) {
            System.out.printf("The file %s is empty, so we will use the default input.\n", fileName);
            return DEFAULT_INPUT;
        }
        System.out.printf("The file %s has been read successfully.\n", fileName);
        return fileContent;
    }

    public static long[] solve(String input) {
        Predicate<String> isInvalid1 = (value) -> {
            int len = value.length();
            if (len % 2 != 0) {
                return false;
            }
            String firstHalf = value.substring(0, len / 2);
            String secondHalf = value.substring(len / 2);
            return firstHalf.equals(secondHalf);
        };

        Predicate<String> isInvalid2 = (value) -> {
            int len = value.length();
            for (int k = 1; k <= len / 2; ++k) {
                if (len % k == 0) {
                    String pattern = value.substring(0, k);
                    int repetitions = len / k;
                    if (pattern.repeat(repetitions).equals(value)) {
                        return true;
                    }
                }
            }
            return false;
        };

        long totalSum1 = 0, totalSum2 = 0;
        String[] ranges = input.trim().replace("\n", "").replace("\r", "").split(",");
        for (String range : ranges) {
            if (range.isBlank()) {
                continue;
            }
            try {
                String[] parts = range.split("-");
                long start = Long.parseLong(parts[0].trim());
                long end = Long.parseLong(parts[1].trim());

                for (long index = Math.min(start, end); index <= Math.max(start, end); ++index) {
                    String str = String.valueOf(index);
                    if (isInvalid1.test(str)) {
                        totalSum1 += index;
                    }
                    if (isInvalid2.test(str)) {
                        totalSum2 += index;
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.printf("Error while parsing the range %s, we will skip it...\n", range);
            }
        }
        return new long[]{totalSum1, totalSum2};
    }
}