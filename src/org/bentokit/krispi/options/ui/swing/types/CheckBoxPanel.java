package org.bentokit.krispi.options.ui.swing.types;

import org.bentokit.krispi.options.Option;
import org.bentokit.krispi.options.types.BooleanOption;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

public class CheckBoxPanel extends JCheckBox {
    public CheckBoxPanel(final Option<Boolean> option) {
        this.setSelected(option.getValue());

        this.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                option.setValue(((JCheckBox)e.getSource()).isSelected());
            }
        });
    }
}
