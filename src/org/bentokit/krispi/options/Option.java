package org.bentokit.krispi.options;

import org.bentokit.krispi.options.util.Pref;

public abstract class Option<T> {
    String prefName;
    String displayName;
    String tooltip;
    T defaultValue;

    public Option(String displayName, String tooltip, String prefName, T defaultValue) {
        this.prefName = prefName;
        this.displayName = displayName;
        this.tooltip = tooltip;
        this.defaultValue = defaultValue;
    }
    

    /** 
     * @returns - The value that the option will be stored as, using the plugin prefix, usually "[appname].plugins.[pluginname].[prefName]"
     **/
    public String getPrefName() { return(this.prefName); }  

    /** 
     * @returns - A string that will be displayed to the user as the label of the option.
     **/
    public String getDisplayName() { return(this.displayName); }

    /** 
     * value - A value to set as the preference.
     **/
    public void setValue(T value) { Pref.setPref(this.prefName,(Object)value); }

    /** 
     * @returns - The value that matches this option.
     **/
    public T getValue() { return((T)Pref.getPref(this.prefName,this.defaultValue)); }
}
