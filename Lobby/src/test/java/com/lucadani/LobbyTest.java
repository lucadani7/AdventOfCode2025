package com.lucadani;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LobbyTest {
    @Test
    void testPart1Simple() {
        List<String> input = Collections.singletonList("1928");
        long result = Lobby.solve(input, 2);
        assertEquals(98, result, "Part 1 simple extraction failed");
    }

    @Test
    void testPart2LargeNumbers() {
        List<String> input = Collections.singletonList("000999999999999");
        long result = Lobby.solve(input, 12);
        assertEquals(999999999999L, result, "Part 2 12-digit extraction failed");
    }

    @Test
    void testOrderPersistence() {
        // From "4912", if we need 2 digits, we can't pick 9 then nothing.
        // We must be able to pick 2 digits. Here 92 is the winner.
        List<String> input = Collections.singletonList("4912");
        long result = Lobby.solve(input, 2);
        assertEquals(92, result, "Greedy order persistence failed");
    }

    @Test
    void testShortStringsAreSkipped() {
        List<String> input = Arrays.asList("123", "1");
        long result = Lobby.solve(input, 2); // "1" should be skipped
        assertEquals(23, result, "Short string filtering failed");
    }

    @Test
    void testAllSameDigits() {
        List<String> input = Collections.singletonList("55555");
        long result = Lobby.solve(input, 3);
        assertEquals(555, result, "Identical digits extraction failed");
    }
}