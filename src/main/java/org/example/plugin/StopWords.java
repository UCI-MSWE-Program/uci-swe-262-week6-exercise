package org.example.plugin;

import module java.base;

/**
 * Loads the shared stop-word list bundled on the classpath.
 */
public final class StopWords {

    private static final String RESOURCE = "/stop_words.txt";

    private static final Set<String> STOP_WORDS = loadFromResource();

    private StopWords() {
        // Utility class, not meant to be instantiated.
    }

    /**
     * @return an immutable set of stop words
     */
    public static Set<String> load() {
        return STOP_WORDS;
    }

    private static Set<String> loadFromResource() {
        Set<String> stopWords = new HashSet<>();
        try (var in = StopWords.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Stop-word resource not found: " + RESOURCE);
            }
            var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split(",")) {
                    String trimmed = word.trim();
                    if (!trimmed.isEmpty()) {
                        stopWords.add(trimmed.toLowerCase());
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read stop words", e);
        }

        return Set.copyOf(stopWords);
    }
}
