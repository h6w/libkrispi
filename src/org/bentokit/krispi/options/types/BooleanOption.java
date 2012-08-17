package org.bentokit.krispi.options.types;

import org.bentokit.krispi.options.Option;

public class BooleanOption extends Option<Boolean> {
    public BooleanOption(String displayName, String tooltip, String prefName, Boolean defaultValue) {
        super(displayName, tooltip, prefName, defaultValue);
    }
}
