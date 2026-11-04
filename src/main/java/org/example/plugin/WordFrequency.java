package org.example.plugin;

/**
 * A single entry in a frequency ranking: a key and how many times it appeared.
 *
 * <p>For the "normal" counting plugin the key is a word; for the "first
 * letter" counting plugin the key is a single letter ({@code "a"}, {@code "b"},
 * ...).</p>
 *
 * @param word  the counted key (a word or a first letter)
 * @param count how many times it occurred
 */
public record WordFrequency(String word, int count) {

    @Override
    public String toString() {
        return word + "  -  " + count;
    }
}
