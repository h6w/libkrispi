package org.bentokit.krispi.options.ui.swing.types;

import org.bentokit.krispi.options.Option;
import org.bentokit.krispi.options.types.ChoiceOption;
import org.bentokit.krispi.options.types.StringOption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import info.clearthought.layout.TableLayout;


public class RadioPanel extends JPanel {
    public RadioPanel(final ChoiceOption option) {
        double size[][] = new double[2][];
        size[0] = new double[2];  //Column widths
        size[1] = new double[option.getOptions().size()];  //Row heights
        size[0][0] = TableLayout.PREFERRED;
        size[0][1] = TableLayout.PREFERRED;

        for (int i = 0; i < option.getOptions().size(); i++)
            size[1][i] = TableLayout.PREFERRED;

        TableLayout layout = new TableLayout(size);
        this.setLayout(layout);

        ButtonGroup group = new ButtonGroup();

        int i = 0;
        
        for (StringOption radiooption : option.getOptions()) {
            JRadioButton button = new JRadioButton(radiooption.getDisplayName(),(option.getValue() == radiooption.getValue()));

            final String result = radiooption.getValue();
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    option.setValue(result);
                }
            });
            group.add(button);
            this.add(button,"0,"+i);
            i++;
        }
    }
}
