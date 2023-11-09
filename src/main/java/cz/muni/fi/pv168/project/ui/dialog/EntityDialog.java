package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public abstract class EntityDialog<E> extends JPanel{

    EntityDialog(Dimension preferredSize) {
        this.setPreferredSize(preferredSize);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        this.add(label);
        this.add(component, "wmin 250lp, grow");
    }

    void addAsScrollable(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        this.add(label);
        this.add(new JScrollPane(component), "wmin 250lp, grow");
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, this, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }

    public Optional<E> showWithCustomButtonText(JComponent parentComponent, String title, Object[] selectionValues, Object initialSelectionValue) {
        int result = JOptionPane.showOptionDialog(parentComponent, this, title,
                JOptionPane.DEFAULT_OPTION, PLAIN_MESSAGE, null, selectionValues, initialSelectionValue);
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }

    static void limitComponentToOneRow(JComponent component) {

        Dimension preferredSize = component.getPreferredSize();
        preferredSize.height = component.getFontMetrics(component.getFont()).getHeight() + 4;
        component.setPreferredSize(preferredSize);
        component.setMaximumSize(preferredSize);
    }
}
