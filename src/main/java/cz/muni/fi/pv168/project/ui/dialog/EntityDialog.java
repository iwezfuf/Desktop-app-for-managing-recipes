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
    E entity;

    EntityDialog(E entity,
                 EntityTableModelProvider entityTableModelProvider,
                 Validator<E> entityValidator) {
        this.entity = entity;
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

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {

        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        if (result == OK_OPTION) {

            var entity = getEntity();

            if (entity != null) { // if the entity is null, it signals that user has set wrong data in the dialog
                return Optional.of(entity);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

    }
}
