package com.lucadani;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CafeteriaTest {

    @Test
    void testPart1_ExampleData() {
        List<Range> ranges = List.of(
                new Range(10, 20),
                new Range(50, 60)
        );
        List<Long> availableIds = List.of(15L, 25L, 55L, 100L);

        // 15 is in [10-20], 55 is in [50-60]. Total = 2.
        long result = Cafeteria.solvePart1(ranges, availableIds);
        assertEquals(2, result, "Part 1 should count IDs within ranges");
    }

    @Test
    void testPart2_OverlappingRanges() {
        // Ranges: 10-20 and 15-25 (overlap)
        // Combined unique IDs: 10 to 25 = 16 IDs
        List<Range> ranges = List.of(
                new Range(10, 20),
                new Range(15, 25)
        );
        long result = Cafeteria.solvePart2(ranges);
        assertEquals(16, result, "Part 2 should merge overlapping ranges");
    }

    @Test
    void testPart2_DisjointRanges() {
        // Ranges: 1-5 (5 IDs) and 10-12 (3 IDs)
        // Total = 8
        List<Range> ranges = List.of(
                new Range(1, 5),
                new Range(10, 12)
        );
        long result = Cafeteria.solvePart2(ranges);
        assertEquals(8, result, "Part 2 should sum disjoint ranges correctly");
    }

    @Test
    void testPart2_NestedRanges() {
        // Range 10-50 completely contains 20-30
        List<Range> ranges = List.of(
                new Range(10, 50),
                new Range(20, 30)
        );
        long result = Cafeteria.solvePart2(ranges);
        assertEquals(41, result, "Part 2 should handle nested ranges (41 IDs including 10 and 50)");
    }
}
