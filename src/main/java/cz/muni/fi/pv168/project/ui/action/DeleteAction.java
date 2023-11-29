package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public final class DeleteAction extends AbstractAction {

    private JTable entityTable;

    public DeleteAction(JTable entityTable) {
        super("Delete", Icons.DELETE_ICON);
        this.entityTable = entityTable;
        putValue(SHORT_DESCRIPTION, "Deletes selected entities");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = showDeleteConfirmationDialog();
        if (option == JOptionPane.YES_OPTION) {
            var entityTableModel = (EntityTableModel) entityTable.getModel();
            Arrays.stream(entityTable.getSelectedRows())
                    .map(entityTable::convertRowIndexToModel)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .forEach(entityTableModel::deleteRow);
        }
    }

    private int showDeleteConfirmationDialog() {
        return JOptionPane.showConfirmDialog(
                entityTable,
                "Are you sure you want to delete the selected entries?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public void setCurrentTable(JTable table) {
        this.entityTable = table;
    }
}
