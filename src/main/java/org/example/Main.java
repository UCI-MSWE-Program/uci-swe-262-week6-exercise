package org.example;

import module java.base;

import org.example.plugin.FrequenciesPlugin;
import org.example.plugin.Plugin;
import org.example.plugin.WordFrequency;
import org.example.plugin.WordsPlugin;

/**
 * Entry point for the "Plugins" exercise.
 *
 * <p>The program reads {@code config.properties} to learn the names of the
 * word-extraction and frequency-counting plugins to use, loads those plugins
 * dynamically through the {@link ServiceLoader}, extracts words from the text
 * file given as the first command-line argument, and prints the top-25
 * frequencies.</p>
 *
 * <p>The program depends only on the {@link WordsPlugin} and
 * {@link FrequenciesPlugin} interfaces; concrete implementations can be added
 * or swapped without recompiling this class.</p>
 *
 */
public final class Main {

    private static final String CONFIG_FILE = "config.properties";

    void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java -cp <classpath> org.example.Main <path-to-text-file>");
            System.exit(1);
        }
        String pathToFile = args[0];

        Properties config = loadConfig();

        String wordsName = config.getProperty("words", "words1");
        String frequenciesName = config.getProperty("frequencies", "frequencies1");

        WordsPlugin wordsPlugin = loadPlugin(WordsPlugin.class, wordsName);
        FrequenciesPlugin frequenciesPlugin = loadPlugin(FrequenciesPlugin.class, frequenciesName);

        List<String> words = wordsPlugin.extractWords(pathToFile);
        List<WordFrequency> frequencies = frequenciesPlugin.top25(words);

        frequencies.forEach(System.out::println);
    }

    private Properties loadConfig() throws IOException {
        Properties config = new Properties();
        try (InputStream in = Files.newInputStream(Path.of(CONFIG_FILE))) {
            config.load(in);
        }
        return config;
    }

    /**
     * Find and instantiate the plugin of the given type whose {@link Plugin#name()}
     * matches {@code name}.
     */
    private <T extends Plugin> T loadPlugin(Class<T> type, String name) {
        for (T plugin : ServiceLoader.load(type)) {
            if (name.equals(plugin.name())) {
                return plugin;
            }
        }
        throw new IllegalStateException(
                "No " + type.getSimpleName() + " plugin named '" + name + "' found.");
    }
}
