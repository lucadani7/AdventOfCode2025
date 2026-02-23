package com.lucadani;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cafeteria {
    /**
     * Counts the number of IDs from the provided list that are contained within any of the given ranges.
     *
     * @param ranges a list of {@code Range} objects, each representing an inclusive range of values
     * @param ids    a list of {@code Long} IDs to be checked against the ranges
     * @return the count of IDs that fall within at least one of the specified ranges
     */
    public static long solvePart1(List<Range> ranges, List<Long> ids) {
        return ids.stream().filter(id -> ranges.stream().anyMatch(r -> r.contains(id))).count();
    }

    /**
     * Merges overlapping or adjacent ranges and calculates the total sum of lengths
     * of the resulting merged ranges.
     *
     * @param ranges a list of {@code Range} objects, each representing an inclusive range of values
     * @return the total sum of lengths of the merged ranges
     */
    public static long solvePart2(List<Range> ranges) {
        if (ranges == null || ranges.isEmpty()) {
            return 0;
        }
        // Copy first: callers may pass an unmodifiable list (e.g., List.of(...)).

        List<Range> sorted = new ArrayList<>(ranges);
        sorted.sort(Comparator.comparingLong(Range::start));
        List<Range> merged = new ArrayList<>();
        Range current = new Range(sorted.getFirst().start(), sorted.getFirst().end());
        for (int i = 1; i < sorted.size(); i++) {
            Range next = sorted.get(i);

            // Merge if overlapping or adjacent (inclusive ranges).
            if (next.start() <= current.end() + 1) {
                long mergedEnd = Math.max(current.end(), next.end());
                current = new Range(current.start(), mergedEnd);
            } else {
                merged.add(current);
                current = new Range(next.start(), next.end());
            }
        }
        merged.add(current);
        return merged.stream().mapToLong(Range::length).sum();
    }

    public static void main(String[] args) {
        try {
            String input = Files.readString(Paths.get("Cafeteria/input.txt")).trim();
            String[] sections = input.split("\n\n");
            List<Range> ranges = new ArrayList<>();
            for (String line : sections[0].split("\n")) {
                String[] parts = line.split("-");
                ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            }
            List<Long> availableIds = new ArrayList<>();
            for (String line : sections[1].split("\n")) {
                availableIds.add(Long.parseLong(line.trim()));
            }
            System.out.printf("Part 1: %d\n", solvePart1(ranges, availableIds));
            System.out.printf("Part 2: %d\n", solvePart2(ranges));
        } catch (IOException e) {
            System.err.println("Could not find input.txt. Please ensure it is in the project root.");
        }
    }
}