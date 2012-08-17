package org.bentokit.krispi.options.types;

import org.bentokit.krispi.options.Option;
import java.io.File;

public class FileOption extends Option<File> {
    public FileOption(String displayName, String tooltip, String prefName, File defaultValue) {
        super(displayName, tooltip, prefName, defaultValue);
    }
}
