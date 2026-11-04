package org.example.plugin;

import module java.base;

/**
 * Computes a frequency ranking from a list of words.
 *
 * <p>The main program depends only on this interface; which concrete
 * counting strategy is used is decided at run time via the configuration
 * file and the {@link ServiceLoader}.</p>
 */
public interface FrequenciesPlugin extends Plugin {

    /**
     * Return the 25 most frequent entries, most frequent first.
     *
     * @param words the words to count
     * @return up to 25 {@link WordFrequency} entries sorted by descending count
     */
    List<WordFrequency> top25(List<String> words);
}
