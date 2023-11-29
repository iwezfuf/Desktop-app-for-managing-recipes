package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;
import cz.muni.fi.pv168.project.ui.panels.EntityTablePanel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;

public final class AddAction<T extends Entity> extends AbstractAction {

    private EntityTablePanel<T> entityTablePanel;
    private final EntityTableModelProviderWithCrud entityTableModelProviderWithCrud;

    public AddAction(
            EntityTablePanel<T> entityTablePanel,
            EntityTableModelProviderWithCrud entityTableModelProviderWithCrud) {
        super("Add", Icons.ADD_ICON);
        this.entityTablePanel = entityTablePanel;
        this.entityTableModelProviderWithCrud = entityTableModelProviderWithCrud;
        putValue(SHORT_DESCRIPTION, "Adds new entity");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        T entityInstance = entityTablePanel.getEntityInstance();
        openDialog(entityInstance);
    }

    private void openDialog(T entity) {

        EntityDialog<T> dialog = entityTablePanel.createDialog(entity, entityTableModelProviderWithCrud);
        Optional<T> result = dialog.show(entityTablePanel.getTable(), "Add Entity");

        if (result.isPresent()) {
            addEntryToTable(result.get());
        } else {
            int option = showInvalidDataConfirmationDialog();
            if (option == JOptionPane.YES_OPTION) {
                openDialog(entity);
            }
        }
    }

    private void addEntryToTable(T entity) {
        // First check if there already exists an entry with the same name
        var entityTableModel = entityTablePanel.getEntityTableModel();
        if (entityTableModel.nameExist(entity.getName())) {
            int option = showDuplicateConfirmationDialog();
            if (option == JOptionPane.NO_OPTION) {
                EntityDialog<T> dialog = entityTablePanel.createDialog(entity, entityTableModelProviderWithCrud);
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
                options[2] // Defaults to "Cancel"
        );
    }

    private int showInvalidDataConfirmationDialog() {
        Object[] options = {"Yes", "No", "Cancel"};
        return JOptionPane.showOptionDialog(
                null,
                "Invalid data were entered. \nDo you want to come back to the dialog?",
                "Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1] // Defaults to "Cancel"
        );
    }


    public void setCurrentTablePanel(EntityTablePanel<T> panel) {
        this.entityTablePanel = panel;
    }
}
