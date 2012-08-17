package org.bentokit.krispi.options.ui.swing.types;

import org.bentokit.krispi.options.types.StringOption;
import org.bentokit.krispi.options.Option;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;


public class DirectoryPanel extends JPanel {
    final JLabel label;

    public DirectoryPanel(final Option<File> option) {
        label = new JLabel();
        label.setText(option.getValue().getPath());
        this.add(label);
        JButton button = new JButton("Browse");
        this.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    option.setValue(chooser.getSelectedFile());
                    label.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
    }
}
