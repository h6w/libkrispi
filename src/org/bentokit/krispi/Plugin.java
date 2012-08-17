/*
* Copyright 2011 by Bentokit Project <tudor@bentokit.org>
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package org.bentokit.krispi;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import org.bentokit.krispi.options.Optionable;
import org.bentokit.krispi.options.OptionGroup;

import javax.swing.Icon;

/**
 * @author Bentokit Project <tudor@bentokit.org>
 *
 */
public class Plugin implements Serializable, Optionable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1097842061313960032L;

	public static final Class<Plugin> TYPE = Plugin.class;

	//final PluginManager manager;
	String name, description, packageName, className, minSoftwareVersion, maxSoftwareVersion;
    URI uri;
	Icon icon;
	org.bentokit.krispi.Plugin activeClass;  //Place to store instantiated class when active. 

    public Plugin(Plugin p) {
        //this(p.getManager(),p.getURI(),p.getName(),p.getDescription(),p.getPackageName(),p.getClassName(),p.getIcon(),
        this(p.getURI(),p.getName(),p.getDescription(),p.getPackageName(),p.getClassName(),p.getIcon(),
              p.minSoftwareVersion(), p.maxSoftwareVersion());
    }

//	public Plugin(PluginManager manager, URI uri, String name, String description, String packageName, String className, Icon icon,
	public Plugin(URI uri, String name, String description, String packageName, String className, Icon icon,
			String minSoftwareVersion, String maxSoftwareVersion) {
		//this.manager = manager;
		this.name = name;
		this.uri = uri;
		this.description = description;
		this.packageName = packageName;
		this.className = className;
		this.minSoftwareVersion = minSoftwareVersion;
		this.maxSoftwareVersion = maxSoftwareVersion;
		this.icon = icon;
		this.activeClass = null;
	}
	
	/**
	 * @return Plugin Manager of the plugin.
	 */
/*
	public PluginManager getManager() {
		return this.manager;
	}
*/


	/**
	 * @return Name of the plugin.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return URI of the plugin.
	 */
	public URI getURI() {
		return this.uri;
	}

	/**
	 * @return Name of the class derived from Plugin.
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * @return Name of the class derived from Plugin.
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * @return Description of the plugin.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return Minimum software version required to run the plugin.
	 */
	public String minSoftwareVersion() {
		return this.minSoftwareVersion;
	}

	/**
	 * @return Maximum software version required to run the plugin. (Can be null.)
	 */
	public String maxSoftwareVersion() {
		return this.maxSoftwareVersion;
	}

	/**
	 * @return Icon for plugin.
	 */
	public Icon getIcon() {
		return this.icon;
	}

    /**
     * @ The stack trace triggers with appropriate method calls.
     */

    public Map<StackTraceElement,Tuple<Object,Method>> getStackTraceLibrary() {
        if (this.activeClass != null) {
            System.err.println("Plugin "+this.name+" active when getting Stack Trace library.");
            return(((Plugin)this.activeClass).getStackTraceLibrary());
        }
        else System.err.println("Plugin "+this.name+" not active when getting Stack Trace library.");
        return(new HashMap<StackTraceElement,Tuple<Object,Method>>());
    }

    /**
     * @ The AppPath triggers with appropriate method calls.
     */

    public Map<AppPath,Tuple<Object,Method>> getAppPathLibrary() {
        if (this.activeClass != null) {
            System.err.println("Plugin "+this.name+" active when getting AppPath library.");
            return(((Plugin)this.activeClass).getAppPathLibrary());
        }
        else System.err.println("Plugin "+this.name+" not active when getting AppPath library.");
        return(new HashMap<AppPath,Tuple<Object,Method>>());
    }

	/**
	 * @return Available settings.
	 */
	public Collection<OptionGroup> getSettings() {
        if (this.activeClass != null) {
            System.err.println("Plugin "+this.name+" active when getting Options.");
            return(((Plugin)this.activeClass).getSettings());
        }
        else System.err.println("Plugin "+this.name+" not active when getting Options.");
        return(null);
	}
	
	public boolean setActive(boolean active) {
        if (active) {
            System.err.println("Activating plugin "+this.getName());
		    //Preferences.userRoot().putBoolean("/"+this.manager.getAppName()+"/plugins/"+this.getName()+"/isActive", active);

		    //Using URLClassLoader for future use of network plugins.
		    URL[] urls = new URL[1];
		    try {
			    urls[0] = this.uri.toURL();
                System.err.println("Loading class from JAR file:"+urls[0].toString());
		    } catch (MalformedURLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    }
		    ClassLoader loader = new URLClassLoader(urls);
	        try {
			    Class<?> cl = loader.loadClass(this.getPackageName()+"."+this.getClassName());
                Constructor<?> c = cl.getConstructor(new Class[]{Plugin.TYPE});
                this.activeClass = (Plugin) c.newInstance(this);
                if (this.activeClass == null) System.err.println("Resulting plugin was NULL while activating plugin "+this.getName()+"!");
                else System.err.println("Plugin "+this.getName()+" activated.");            			
		    } catch (InstantiationException e) {
                System.err.println("Could not instantiate class "+this.getPackageName());
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    } catch (IllegalAccessException e) {
                System.err.println("Illegal access while trying to instantiate class "+this.getPackageName());
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    } catch (ClassNotFoundException e) {
                System.err.println("Class not found "+this.getPackageName()+"."+this.getClassName());
			    // TODO Auto-generated catch block
			    e.printStackTrace();
            } catch (NoSuchMethodException nsme) {
                System.err.println("No such constructor "+this.getPackageName()+"."+this.getClassName()+"(Plugin)");
            } catch (InvocationTargetException ite) {
                System.err.println("InvocationTargetException while constructing "+this.getPackageName()+"."+this.getClassName()+"(Plugin)");
            }
        } else {
            System.err.println("Deactivating plugin "+this.getName());
            this.activeClass = null;
            System.err.println("Plugin "+this.getName()+" deactivated.");            
        }
		return(true);
	}

	public boolean isActive() {
		return(this.activeClass != null);
	}

    public Plugin getActiveObject() {
        return(this.activeClass);
    }

    public Collection<Object> hook(AppPath path) {
        /*
        AppPath testpath = new AppPath("ClickMe");
        System.err.print(path);
        if (testpath.equals(path)) System.err.print(" equals ");
        else System.err.print(" does not equal ");
        System.err.println(testpath);
        if (this.activeAppPathHooks.containsKey(path)) System.err.println("AppHooks contains key "+path);
        else  System.err.println("AppHooks does not contain key "+path);
        */

        Tuple<Object,Method> hooks = this.getAppPathLibrary().get(path);
        System.err.print("Plugin "+this.name+" activating call at "+path+" hook.");
        HashMap<String,Tuple<Object,Method>> hookmap = new HashMap<String,Tuple<Object,Method>>();
        hookmap.put(this.name,hooks);
		return(PluginManager.call(hookmap));        
    }
}
