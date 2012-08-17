package org.bentokit.krispi.options.types;

import org.bentokit.krispi.options.Option;

public class StringOption extends Option<String> {
    public StringOption(String displayName, String tooltip, String prefName, String defaultValue) {
        super(displayName, tooltip, prefName, defaultValue);
    }
}
