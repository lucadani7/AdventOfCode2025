package com.lucadani;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftShopTest {
    @Test
    void testOfficialExample() {
        long[] results = GiftShop.solve("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528," +
                "446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124");
        assertEquals(1227775554L, results[0], "Part 1 result should match example");
        assertEquals(4174379265L, results[1], "Part 2 result should match example");
    }

    @Test
    void testPart1Logic() {
        // 10-13: contains 11 (valid for Part 1)
        // 1210-1215: contains 1212 (valid for Part 1)
        // 100-105: contains 101 (invalid for Part 1, odd length)
        long[] results = GiftShop.solve("10-13, 1210-1215, 100-105");
        assertEquals(1223L, results[0]); // 11 + 1212
    }

    @Test
    void testPart2Logic() {
        long[] results = GiftShop.solve("110-112, 121210-121214");

        // Part 1 Sum:
        // 111 (invalid, odd length)
        // 121212 (invalid, 121 != 212)
        assertEquals(0L, results[0]);

        // Part 2 Sum:
        // 111 (valid)
        // 121212 (valid)
        // Sum = 111 + 121212 = 121323
        assertEquals(121323L, results[1]);
    }

    @Test
    void testMalformedInput() {
        // a valid range and two invalid ranges
        long[] results = GiftShop.solve("11-11, abc-def, 50-hashdahs");
        assertArrayEquals(new long[]{11, 11}, results);
    }

    @Test
    void testEmptyInput() {
        long[] results = GiftShop.solve("   "); // only spaces
        assertArrayEquals(new long[]{0, 0}, results);
    }

    @Test
    void testReversedRange() {
        // the range [12, 10] is the same as [10, 12]
        long[] results = GiftShop.solve("12-10");
        assertArrayEquals(new long[]{11, 11}, results);
    }

    @Test
    void testOneSingleDigit() {
        long[] results = GiftShop.solve("0-9, 9-0");
        assertArrayEquals(new long[]{0, 0}, results);
    }
}
