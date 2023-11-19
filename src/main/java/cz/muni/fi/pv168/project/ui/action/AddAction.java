package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.panels.EntityTablePanel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

public final class AddAction<T extends Entity> extends AbstractAction {

    private EntityTablePanel<T> entityTablePanel;
    private final EntityTableModelProvider entityTableModelProvider;

    public AddAction(
            EntityTablePanel<T> entityTablePanel,
            EntityTableModelProvider entityTableModelProvider) {
        super("Add", Icons.ADD_ICON);
        this.entityTablePanel = entityTablePanel;
        this.entityTableModelProvider = entityTableModelProvider;
        putValue(SHORT_DESCRIPTION, "Adds new entity");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Class<T> type = entityTablePanel.getType();
        T entityInstance;
        try {
            entityInstance = type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        EntityDialog<T> dialog = createDialog(entityInstance);
        dialog.show(entityTablePanel.getTable(), "Add Entity")
                .ifPresent(this::addEntryToTable);
    }

    private EntityDialog<T> createDialog(T entityInstance) {
        EntityDialog<T> dialog;
        Class<T> type = entityTablePanel.getType();
        try {
            dialog = entityTablePanel.getEntityDialog().getConstructor(
                    type, EntityTableModelProvider.class, Validator.class
            ).newInstance(
                    entityInstance,
                    entityTableModelProvider,
                    entityTablePanel.getEntityValidator());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException("Failed to create dialog.", ex);
        }
        return dialog;
    }

    private void addEntryToTable(T entity) {
        // First check if there already exists an entry with the same name
        var entityTableModel = entityTablePanel.getEntityTableModel();
        if (entityTableModel.nameExist(entity.getName())) {
            int option = showDuplicateConfirmationDialog();
            if (option == JOptionPane.NO_OPTION) {
                EntityDialog<T> dialog = createDialog(entity);
                dialog.show(entityTablePanel.getTable(), "Add Entity")
                        .ifPresent(this::addEntryToTable);
                return;
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        entityTableModel.addRow(entity);
    }

    private int showDuplicateConfirmationDialog() {
        Object[] options = {"Yes", "No, return to add dialog", "Cancel"};
        return JOptionPane.showOptionDialog(
                null,
                "An entry with the same name already exists. \nDo you want to add the new entity anyways?",
                "Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2] // Default to "Cancel"
        );
    }


    public void setCurrentTablePanel(EntityTablePanel<T> panel) {
        this.entityTablePanel = panel;
    }
}
