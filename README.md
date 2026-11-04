# Plugins / Dependency Injection

The program computes the top-25
word frequencies of a text file using a *plugin* architecture: the main
program depends only on interfaces, and concrete implementations are loaded
dynamically at run time and selected through an external configuration file.

## Environment

- Java 27

## Design

The dynamic loading uses the standard Java
[`ServiceLoader`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ServiceLoader.html)
mechanism. Each plugin is registered in `META-INF/services` so the main program
can discover it without any compile-time reference to it.

| File | Role |
| --- | --- |
| `org.example.plugin.Plugin` | Common contract (`name()`) for every plugin |
| `org.example.plugin.WordsPlugin` | Interface for word extraction |
| `org.example.plugin.FrequenciesPlugin` | Interface for frequency counting |
| `org.example.plugin.WordFrequency` | A `(key, count)` result record |
| `org.example.plugin.StopWords` | Loads `stop_words.txt` from the classpath |
| `org.example.plugin.impl.*` | The concrete plugin implementations |
| `org.example.Main` | Reads `config.properties`, loads plugins, prints results |

### Word-extraction plugins (`words`)

- **`words1`** — the "normal" extraction: lower-case, replace non-word
  characters with spaces, split on whitespace, drop stop words (including the
  single letters `a-z`).
- **`words2`** — same extraction, but keeps only *non-stop words containing
  the letter `z`*.

### Frequency-counting plugins (`frequencies`)

- **`frequencies1`** — the "normal" counting: count occurrences of each word,
  return the top 25 by descending count.
- **`frequencies2`** — counts words by their **first letter** (`a`, `b`, ...)
  and returns the top 25 letters by descending count.

## Configuration

`config.properties` chooses which plugins to load. New implementations can be
added without touching the main program — just add a class, register it in the
corresponding `META-INF/services` file, and reference its name in the config.

```
words=words1
frequencies=frequencies1
```

## Running

```bash
./gradlew run --args="sample.txt"       # uses config.properties defaults
./gradlew run --args="path/to/book.txt"
```

Or build the distribution:

```bash
./gradlew installDist
build/install/*/bin/* path/to/book.txt
```

Stop words are read from `src/main/resources/stop_words.txt` (comma-separated).
The `pride-and-prejudice.txt` sample shipped in `src/main/resources/` can be
used as the input:

```bash
./gradlew run --args="src/main/resources/pride-and-prejudice.txt"
```