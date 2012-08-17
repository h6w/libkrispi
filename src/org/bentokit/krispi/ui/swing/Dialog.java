package org.bentokit.krispi.ui.swing;

import javax.swing.JDialog;

import org.bentokit.krispi.PluginManager;

public class Dialog extends JDialog {
	/**
	 * Generated serialVersionUID - TH 20101115
	 */
	private static final long serialVersionUID = 6203843522613004054L;

	public Dialog(PluginManager manager) {
		super();
		setTitle("Plugins");
		this.add(new Panel(manager));
		this.pack();
	}
}
