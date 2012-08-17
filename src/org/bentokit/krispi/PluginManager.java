/*
* Copyright 2011 by Bentokit Project <tudor@bentokit.org>
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package org.bentokit.krispi;

import java.io.File;

import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.prefs.Preferences;

public class PluginManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4542443167942553538L;

	static String[] reqd = {
			"-Plugin-Name",
			"-Plugin-Description",
			"-Plugin-Class" }; //Required Manifest Parameters	
	
	private Collection<Plugin> plugins;
	private List<URI> directories;
	private String appname;
	final private Map<StackTraceElement,Map<String,Tuple<Object,Method>>> activeStackTraceHooks; //Library of hooks for code locations
    final private Map<AppPath,Map<String,Tuple<Object,Method>>> activeAppPathHooks; //Library of hooks for appPaths
	
    public PluginManager(URI directory, String appname) {
        this(new URI[] { directory } ,appname);
    }

	public PluginManager(URI[] directories, String appname) {
        this(Arrays.asList(directories),appname);
    }

	public PluginManager(List<URI> directories, String appname) {
		this.directories = directories;
		this.plugins = new Vector<Plugin>();
		this.activeStackTraceHooks = new HashMap<StackTraceElement,Map<String,Tuple<Object,Method>>>();
		this.activeAppPathHooks = new HashMap<AppPath,Map<String,Tuple<Object,Method>>>();
		this.appname = appname;
		
        for (URI directory : directories) {
            try {
		        File directoryAsFile = new File(directory.toURL().getFile()); 
		        if (!directoryAsFile.exists()) {
			        System.err.println("Plugin directory "+directoryAsFile.getPath()+" does not exist.");
			        continue;
		        }
		
		        for (File file : directoryAsFile.listFiles(
				        new FilenameFilter() {
					        public boolean accept(File file, String name) {
						        return(name.endsWith(".jar"));
					        }
				        })) {
			        try {
				        Plugin plugin = this.generatePlugin(file,appname);
				        if (plugin != null) {
					        System.err.println(file.getAbsolutePath()+" is a plugin.");
					        this.plugins.add(plugin);
				        } else System.err.println(file.getAbsolutePath()+" is not a plugin.");
			        } catch (IOException e) {
				        // TODO Auto-generated catch block
				        e.printStackTrace();
			        }
		        }
            } catch (MalformedURLException murle) {
		        murle.printStackTrace();
                System.exit(-1);
            }
        }

		for (Plugin plugin : this.plugins) {
			boolean def = false;  //default to false;
            System.err.println(plugin.getName()+" is "+(Preferences.userRoot().getBoolean("/"+this.appname+"/plugins/"+plugin.getName()+"/isActive",def) ? "Active" : "Inactive"));
			this.setActive(plugin,Preferences.userRoot().getBoolean("/"+this.appname+"/plugins/"+plugin.getName()+"/isActive",def));
		}
	}
	
	private Plugin generatePlugin(File file, String appname) throws IOException {
		JarFile jar = new JarFile(file);
		Manifest mf = jar.getManifest();
		Attributes attributes = mf.getMainAttributes();
		System.err.println("Attributes in file "+file.getAbsolutePath()+":");
		for (Object key : attributes.keySet()) {
			System.err.println(key+":"+attributes.get(key));
		}

		//Check for required manifest params.  Return null if one missing. 
		for (String attribute : reqd) {
			if (attributes.getValue(appname+attribute) == null) {
				System.err.println(file.getAbsolutePath()+" does not contain attribute "+appname+attribute);
				return(null);
			}
		}

        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(javax.imageio.ImageIO.read(jar.getInputStream(jar.getEntry("icon.png"))));

		Plugin plugin = new Plugin(
//				this,
                file.toURI(),
				attributes.getValue(appname+"-Plugin-Name"),
				attributes.getValue(appname+"-Plugin-Description"),
				attributes.getValue(appname+"-Plugin-Package"),
				attributes.getValue(appname+"-Plugin-Class"),
                icon,
				null,
				null);
		return(plugin);
	}
	
	public boolean add(Plugin plugin) {
		if (!this.plugins.contains(plugin)) {
			this.plugins.add(plugin);		
			return true;
		} else return false;
	}
	
	public boolean setActive(Plugin plugin, Boolean active) {
        boolean activated = plugin.setActive(active);
        if (active && activated) {
            //Get StackTraceElement triggers from plugin and insert into activeStackTraceHooks with plugin name.
            Map<StackTraceElement,Tuple<Object,Method>> stackTraces = plugin.getStackTraceLibrary();
            if (stackTraces != null) {
                for (StackTraceElement element : stackTraces.keySet()) {
                    if (!activeStackTraceHooks.containsKey(element))
                        activeStackTraceHooks.put(element,new HashMap<String,Tuple<Object,Method>>());
                    activeStackTraceHooks.get(element).put(plugin.getName(),stackTraces.get(element));
                }
            }

            //Get AppPath triggers from plugin and insert into activeAppPathHooks with plugin name.
            Map<AppPath,Tuple<Object,Method>> appPaths = plugin.getAppPathLibrary();
            if (appPaths == null) { System.err.print("0"); } else { System.err.print(appPaths.size()); }
            System.err.println(" appPaths returned by plugin "+plugin.getName());
            if (appPaths != null) {
                for (AppPath path : appPaths.keySet()) {
                    if (!activeAppPathHooks.containsKey(path))
                        activeAppPathHooks.put(path,new HashMap<String,Tuple<Object,Method>>());
                    activeAppPathHooks.get(path).put(plugin.getName(),appPaths.get(path));
                }
            }
            this.printActiveAppPathHooks();
        }
		return(activated);
	}
	
    public void printActiveAppPathHooks() {
        System.err.println("Active AppPath Hooks:");
        for (AppPath path : this.activeAppPathHooks.keySet()) {
            Map<String,Tuple<Object,Method>> hooks = this.activeAppPathHooks.get(path);
            for (String pluginname : hooks.keySet()) {
                System.err.println("    "+path+":"+pluginname+"."+hooks.get(pluginname).second);
            }
        }
        System.err.println("AppPath Hooks Printout Complete.");
    }

    public Plugin getPluginByName(String name) {
        for (Plugin plugin : this.plugins) {
            if (plugin.getName().equals(name))
                return(plugin);
        }
        return(null);
    }

	public Collection<Plugin> getAvailablePlugins() {
		return(plugins);
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

        Map<String,Tuple<Object,Method>> hooks = this.activeAppPathHooks.get(path);
        System.err.print("Activating ");
        if (hooks == null) { System.err.print("0"); } else { System.err.print(hooks.size()); }
        System.err.println(" call(s) at "+path+" hook.");
		return(this.call(hooks));        
    }

	public void hook() {
		this.call(this.activeStackTraceHooks.get(Thread.currentThread().getStackTrace()[2]));		
	}

    public static Collection<Object> call(Map<String,Tuple<Object,Method>> pluginCalls) {
        ArrayList<Object> results = new ArrayList<Object>();
        if (pluginCalls == null) return(results);
		for (String pluginName : pluginCalls.keySet()) {
			try {
                Method method = pluginCalls.get(pluginName).second;
                Object obj = method.invoke(pluginCalls.get(pluginName).first); //null for static, null for no arguments
                if (obj != null) results.add(obj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
        return(results);
    }

	public String getAppName() {
		return this.appname;
	}

	public List<URI> getDirectories() {
		return this.directories;
	}	
}
