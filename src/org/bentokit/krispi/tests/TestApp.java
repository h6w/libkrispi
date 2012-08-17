/*
* Copyright 2011 by Bentokit Project <tudor@bentokit.org>
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package org.bentokit.krispi.tests;

import java.io.File;
import java.net.MalformedURLException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.bentokit.krispi.PluginManager;
import org.bentokit.krispi.AppPath;
import org.bentokit.krispi.ui.swing.Dialog;

public class TestApp extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5858814152326282442L;
	
	PluginManager pluginManager;

    public TestApp() {
       	this.pluginManager = new PluginManager((new File(System.getProperty("user.home")+File.separator+".krispi"+File.separator+"plugins")).toURI(),"TestApp");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton();
        button.add(new JLabel("Click Me!"));
        button.addActionListener(this);
        this.add(button);

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("About");
        JMenuItem pluginMenuItem = new JMenuItem("Plugins");

        this.setJMenuBar(menubar);
        menubar.add(menu);
        menu.add(pluginMenuItem);

        pluginMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dialog pluginDialog = new Dialog(pluginManager);
                pluginDialog.setVisible(true);                        
            }            
        });
    }

    public void actionPerformed(ActionEvent e) {
        this.pluginManager.hook(new AppPath("ClickMe"));
    }

    public static void createAndRunGUI() {
        TestApp app = new TestApp();
        app.pack();
        app.setVisible(true);        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndRunGUI();
            }
        });
    }
}
