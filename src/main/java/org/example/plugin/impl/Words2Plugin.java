package org.example.plugin.impl;

import module java.base;

import org.example.plugin.StopWords;
import org.example.plugin.WordsPlugin;

/**
 * Extraction that keeps only non-stop words containing the letter 'z'.
 * Read the file, replace every non-alpha character with a space,
 * and split on whitespace. Drop stop words (including 1 characters words),
 * and words that do not contain the letter 'z'.
 */
public final class Words2Plugin implements WordsPlugin {

    @Override
    public String name() {
        return "words2";
    }

    @Override
    public List<String> extractWords(String pathToFile) throws IOException {
        String data = Files.readString(Path.of(pathToFile));
        String[] tokens = data.toLowerCase().replaceAll("[^a-zA-Z]+", " ").trim().split("\\s+");

        Set<String> stopWords = StopWords.load();

        List<String> words = new ArrayList<>();
        for (String token : tokens) {
            if (token.length() > 1 && token.indexOf('z') >= 0 && !stopWords.contains(token)) {
                words.add(token);
            }
        }
        return words;
    }
}
