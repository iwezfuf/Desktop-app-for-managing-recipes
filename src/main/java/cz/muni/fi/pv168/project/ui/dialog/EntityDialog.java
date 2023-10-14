package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public abstract class EntityDialog<E> extends JPanel{

    //private final JPanel panel = new JPanel();

    EntityDialog(Dimension preferredSize) {
        this.setPreferredSize(preferredSize);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // TODO this all may be tmp
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

    static void limitComponentToOneRow(JComponent component) { // TODO maybe tmp
        // Limit the preferred and maximum height of the JTextField to one row.
        Dimension preferredSize = component.getPreferredSize();
        preferredSize.height = component.getFontMetrics(component.getFont()).getHeight() + 4; // Adjust as needed.
        component.setPreferredSize(preferredSize);
        component.setMaximumSize(preferredSize);
    }
}
