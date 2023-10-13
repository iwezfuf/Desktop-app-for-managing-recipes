package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();

    EntityDialog(Dimension preferredSize) {
        panel.setPreferredSize(preferredSize);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // TODO this all may be tmp
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        panel.add(label);
        panel.add(component, "wmin 250lp, grow");
    }

    void addAsScrollable(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        panel.add(label);
        panel.add(new JScrollPane(component), "wmin 250lp, grow");
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
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
