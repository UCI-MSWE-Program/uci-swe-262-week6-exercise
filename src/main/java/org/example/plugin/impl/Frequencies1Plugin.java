package org.example.plugin.impl;

import module java.base;

import org.example.plugin.FrequenciesPlugin;
import org.example.plugin.WordFrequency;

/**
 * The "normal" counting: count how many times each word appears, then return
 * the top 25 by descending count.
 */
public final class Frequencies1Plugin implements FrequenciesPlugin {

    private static final int TOP_N = 25;

    @Override
    public String name() {
        return "frequencies1";
    }

    @Override
    public List<WordFrequency> top25(List<String> words) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        return topN(counts);
    }

    /**
     * Select the {@link #TOP_N} entries with the largest counts using a
     * min-heap: the head of the heap is the entry with the smallest count, so
     * it is the one replaced when a larger count arrives. This runs in
     * O(M log TOP_N) instead of sorting all M distinct words in O(M log M).
     */
    private List<WordFrequency> topN(Map<String, Integer> counts) {
        PriorityQueue<WordFrequency> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(WordFrequency::count)
                        .thenComparing(WordFrequency::word));

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            WordFrequency candidate = new WordFrequency(entry.getKey(), entry.getValue());
            if (minHeap.size() < TOP_N) {
                minHeap.add(candidate);
            } else if (candidate.count() > minHeap.peek().count()) {
                minHeap.poll();
                minHeap.add(candidate);
            }
        }

        return minHeap.stream()
                .sorted(Comparator.comparingInt(WordFrequency::count).reversed()
                        .thenComparing(WordFrequency::word))
                .toList();
    }
}
