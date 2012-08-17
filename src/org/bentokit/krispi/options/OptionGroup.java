package org.bentokit.krispi.options;

import java.util.ArrayList;
import java.util.Collection;

public class OptionGroup {
    String name;
    String displayName;
    ArrayList<Option<?>> options = new ArrayList<Option<?>>();

    public OptionGroup(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
    
    public OptionGroup(String name, String displayName, ArrayList<Option> options) {
        this.name = name;
        this.displayName = displayName;
        for (Option option : options)
            this.addOption(option);
    }

    public String getName() { return(this.name); }

    public String getDisplayName() { return(this.displayName); }

    public void addOption(Option<?> option) {
        this.options.add(option);
    }

    public Collection<Option<?>> getOptions() { return(this.options); }
}
