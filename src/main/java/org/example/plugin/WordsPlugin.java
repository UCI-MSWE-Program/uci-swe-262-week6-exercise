package org.example.plugin;

import module java.base;

/**
 * Extracts words from a text file.
 *
 * <p>The main program depends only on this interface; which concrete
 * extraction strategy is used is decided at run time via the configuration
 * file and the {@link ServiceLoader}.</p>
 */
public interface WordsPlugin extends Plugin {

    /**
     * Extract words from the given text file, filtering out stop words.
     *
     * @param pathToFile path to the text file to process
     * @return the extracted words, in the order they appear
     * @throws IOException if the file cannot be read
     */
    List<String> extractWords(String pathToFile) throws IOException;
}
