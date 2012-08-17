package org.bentokit.krispi.options.ui.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import info.clearthought.layout.TableLayout;
import org.bentokit.krispi.options.*;
import org.bentokit.krispi.options.types.*;
import org.bentokit.krispi.options.ui.swing.types.*;

public class ConfigPanel extends JPanel {
    public ConfigPanel(Optionable optionable) throws UnknownOptionTypeException {
        if (optionable.getSettings() == null) return;
        for (OptionGroup group : optionable.getSettings()) {
            Collection<Option<?>> options = group.getOptions();
            if (options != null) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),group.getDisplayName(),
                           TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                           new Font("Sans Serif",Font.BOLD,12)));


                double size[][] = new double[2][];
                size[0] = new double[2];  //Column widths
                size[1] = new double[options.size()];  //Row heights
                size[0][0] = TableLayout.PREFERRED;
                size[0][1] = TableLayout.PREFERRED;

                for (int i = 0; i < options.size(); i++)
                    size[1][i] = TableLayout.PREFERRED;

                TableLayout layout = new TableLayout(size);
                panel.setLayout(layout);
                
                int i = 0;
                for (Option<?> option : options) {
                    panel.add(new JLabel(option.getDisplayName()),"0,"+i);
                    if (option instanceof StringOption)
                        panel.add(new TextFieldPanel((Option<String>)option),"1,"+i);
                    else if (option instanceof BooleanOption)
                        panel.add(new CheckBoxPanel((Option<Boolean>)option),"1,"+i);
                    else if (option instanceof ChoiceOption)
                        panel.add(new RadioPanel((ChoiceOption)option),"1,"+i);
                    else if (option instanceof FileOption)
                        panel.add(new DirectoryPanel((FileOption)option),"1,"+i);
                    else if (option != null) {
                        if (option.getClass() != null) {
                            if (option.getClass().getName() != null)
                                throw new UnknownOptionTypeException(option.getClass().getName());
                            else
                                throw new UnknownOptionTypeException("option.getClass().getName() is null");
                        }
                        else
                            throw new UnknownOptionTypeException("option.getClass() is null");
                    }
                    else
                        throw new UnknownOptionTypeException("NULL!");       
                    i++;             
                }
                this.add(panel);
            }
        }
    }    
}
