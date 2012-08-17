/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/


package org.bentokit.krispi.options.util;

import java.util.prefs.Preferences;


/**
 * User preference object.
 */
public class Pref {
    private static Pref     PREF = null;
    private Preferences     aPreferences = null;


    /**
     * Create preference object.
     *
     * @param prefName
     */
    public Pref(String prefName) {
        PREF = this;
        aPreferences = Preferences.userRoot().node(prefName);
    }


    /**
     * Get preference object.
     *
     * @return
     */
    public static Preferences get() {
        assert PREF != null;
        return PREF.aPreferences;
    }


    /**
     * Get preference value.
     *
     * @return
     */
    public static Object getPref(String key, Object def) {
        if (def instanceof String)
            return getPref(key, (String) def);
        else if (def instanceof Boolean)
            return getPref(key, (Boolean)def);
        else if (def instanceof Double)
            return getPref(key, (Double) def);
        else if (def instanceof Integer)
            return getPref(key, (Integer)def);
        else return(null);
    }

    /**
     * Get preference value.
     *
     * @return
     */
    public static String getPref(String key, String def) {
        return PREF.aPreferences.get(key, def);
    }


    /**
     * Get preference value.
     *
     * @return
     */
    public static Integer getPref(String key, Integer def) {
        return PREF.aPreferences.getInt(key, def);
    }


    /**
     * Get preference value.
     *
     * @return
     */
    public static Double getPref(String key, Double def) {
        return PREF.aPreferences.getDouble(key, def);
    }


    /**
     * Get preference value.
     *
     * @return
     */
    public static Boolean getPref(String key, Boolean def) {
        return PREF.aPreferences.getBoolean(key, def);
    }


    /**
     * Get preference value.
     *
     * @return
     */
    public static Integer getPref(String key, int option, int def) {
        String key2 = String.format("%s%03d", key, option);
        return PREF.aPreferences.getInt(key2, def);
    }


    /**
     * Get preference value.
     *
     * @return
     */
    public static String getPref(String key, int option, String def) {
        String key2 = String.format("%s%03d", key, option);
        return PREF.aPreferences.get(key2, def);
    }


   public static void setPref(String key, Object def) {
        if (def instanceof String)       setPref(key, (String)def);
        else if (def instanceof Boolean) setPref(key, (Boolean)def);
        else if (def instanceof Double)  setPref(key, (Double)def);
        else if (def instanceof Integer) setPref(key, (Integer)def);
    }

    /**
     * Set preference.
     *
     * @param val
     */
    public static void setPref(String key, String val) {
        PREF.aPreferences.put(key, val);
    }


    /**
     * Set preference.
     *
     * @param val
     */
    public static void setPref(String key, Integer val) {
        PREF.aPreferences.putInt(key, val);
    }


    /**
     * Set preference.
     *
     * @param val
     */
    public static void setPref(String key, Double val) {
        PREF.aPreferences.putDouble(key, val);
    }


    /**
     * Set preference.
     *
     * @param val
     */
    public static void setPref(String key, Boolean val) {
        PREF.aPreferences.putBoolean(key, val);
    }
}
