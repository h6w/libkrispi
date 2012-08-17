package org.bentokit.krispi.tests;

import org.bentokit.krispi.AppPath;
import org.bentokit.krispi.Plugin;
import org.bentokit.krispi.Tuple;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TestPlugin extends Plugin {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8983234306482204715L;

	public TestPlugin(Plugin p) { super(p); }

    public Map<AppPath,Tuple<Object,Method>> getAppPathLibrary() {
        Map<AppPath,Tuple<Object,Method>> library = new HashMap<AppPath,Tuple<Object,Method>>();
        try {
            library.put(
                new AppPath("ClickMe"), new Tuple<Object,Method>(this,this.getClass().getMethod("clickMe"))
            );
        } catch (NoSuchMethodException nsme) {
            System.err.println("No such method \"clickMe\"");
        }

        return(library);
    }

    public void clickMe() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new JFrame("Hello World!");
                window.getContentPane().add(new JLabel("I've been clicked!"));
                window.pack();
                window.setVisible(true);
            }
        });
    }
}
