package org.example.plugin;

/**
 * Common contract for every pluggable component.
 *
 * <p>A plugin is identified by a unique {@link #name()}. The main program
 * reads a name from the configuration file and uses it to pick one concrete
 * implementation from among all the implementations registered with the
 * {@link java.util.ServiceLoader}.</p>
 */
public interface Plugin {

    /**
     * Unique, stable name used to select this plugin from the configuration.
     *
     * @return the plugin name, e.g. {@code "words1"}.
     */
    String name();
}
