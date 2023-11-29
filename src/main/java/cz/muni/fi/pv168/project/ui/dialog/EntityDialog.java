package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Objects;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();
    protected final Validator<E> entityValidator;
    protected final EntityTableModelProvider entityTableModelProvider;

    EntityDialog(EntityTableModelProvider entityTableModelProvider,
                 Validator<E> entityValidator) {
        this.entityTableModelProvider = Objects.requireNonNull(entityTableModelProvider);
        this.entityValidator = Objects.requireNonNull(entityValidator);
        panel.setLayout(new MigLayout("wrap 2"));
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

    public JPanel getPanel() {
        return panel;
    }

    abstract E getEntity();

    public abstract void configureReadOnlyMode();

    private Optional<E> showDialog(JComponent parentComponent, String title, Object[] options, int optionType) {
        int result = JOptionPane.showOptionDialog(
                parentComponent, panel, title, optionType, PLAIN_MESSAGE, null, options, options[0]);

        if (result == JOptionPane.OK_OPTION) {
            var entity = getEntity();
            if (entity != null) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }
    public Optional<E> showForView(JComponent parentComponent, String title) {
        Object[] options = {"OK"};
        return showDialog(parentComponent, title, options, JOptionPane.DEFAULT_OPTION);
    }
    public Optional<E> show(JComponent parentComponent, String title) {
        Object[] options = {"OK", "Cancel"};
        return showDialog(parentComponent, title, options, JOptionPane.OK_CANCEL_OPTION);
    }


}
