package org.bentokit.krispi.options.types;

import java.util.ArrayList;
import org.bentokit.krispi.options.Option;
import org.bentokit.krispi.options.types.StringOption;

public class ChoiceOption extends Option<String> {
    ArrayList<StringOption> options;

    public ChoiceOption(String displayName, String tooltip, String prefName, String defaultValue, ArrayList<StringOption> options) {
        super(displayName, tooltip, prefName, defaultValue);
        this.options = options;
    }

    public ArrayList<StringOption> getOptions() { return(this.options); }    
}
