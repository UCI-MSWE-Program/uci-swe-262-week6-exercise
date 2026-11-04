package org.example.plugin.impl;

import module java.base;

import org.example.plugin.StopWords;
import org.example.plugin.WordsPlugin;

/**
 * The "normal" word extraction: read the file, replace every non-alpha
 * character with a space, and split on whitespace. Drop stop words,
 * including 1 characters words.
 */
public final class Words1Plugin implements WordsPlugin {

    @Override
    public String name() {
        return "words1";
    }

    @Override
    public List<String> extractWords(String pathToFile) throws IOException {
        String data = Files.readString(Path.of(pathToFile));
        String[] tokens = data.toLowerCase().replaceAll("[^a-zA-Z]+", " ").trim().split("\\s+");

        Set<String> stopWords = StopWords.load();

        List<String> words = new ArrayList<>();
        for (String token : tokens) {
            if (token.length() > 1 && !stopWords.contains(token)) {
                words.add(token);
            }
        }
        return words;
    }
}
