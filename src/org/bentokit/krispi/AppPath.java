/*
* Copyright 2011 by Bentokit Project <tudor@bentokit.org>
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package org.bentokit.krispi;

public class AppPath implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7630057336248999159L;
	String[] location;

    public AppPath(String[] location) { this.location = location; }
    public AppPath(String location) { this(new String[] { location }); }

    public String[] getLocation() { return(this.location); }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String step : location) {
            builder.append("/"+step);
        }
        return(builder.toString());
    }

    @Override
    public int hashCode() {
        return(this.toString().length()); 
    }

    @Override
    public boolean equals(Object otherobj) {
        //System.err.print("Comparing "+this.toString()+" to "+otherobj.toString()+":");
        boolean result = false;
        if (otherobj instanceof AppPath) {
            String[] otherlocation = ((AppPath) otherobj).getLocation();
            if (location.length == otherlocation.length) {
                result = true;
                for (int i = 0; i < location.length; i++) {
                    if (!this.location[i].equals(otherlocation[i])) { result = false; break; }
                }
            }
        }
        //if (result) System.err.println("true");
        //else System.err.println("false");
        return(result);   
    }
}
