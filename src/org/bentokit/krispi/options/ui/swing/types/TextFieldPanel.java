package org.bentokit.krispi.options.ui.swing.types;

import org.bentokit.krispi.options.types.StringOption;
import org.bentokit.krispi.options.Option;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class TextFieldPanel extends JTextField {
    public TextFieldPanel(final Option<String> option) {
        this.setText(option.getValue());

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option.setValue(((JTextField)e.getSource()).getText());
            }
        });
    }
}
