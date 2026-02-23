package com.lucadani;

public record Range(long start, long end) {
    /**
     * Checks if the specified value is within the range represented by this object.
     *
     * @param id the value to check for inclusion in the range
     * @return true if the value is within the range, inclusive of the start and end; false otherwise
     */
    public boolean contains(long id) {
        return id >= start && id <= end;
    }

    /**
     * Calculates the total number of elements in the range, inclusive of the start and end values.
     *
     * @return the length of the range as a positive long value
     */
    public long length() {
        return end - start + 1;
    }
}
