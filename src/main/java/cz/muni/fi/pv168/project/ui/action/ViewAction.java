package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.panels.EntityTablePanel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ViewAction<T extends Entity> extends AbstractAction {

    private EntityTablePanel<T> entityTablePanel;
    private final EntityTableModelProvider entityTableModelProvider;

    public ViewAction(
            EntityTablePanel<T> entityTablePanel,
            EntityTableModelProvider entityTableModelProvider) {
        super("View", Icons.VIEW_ICON);
        this.entityTablePanel = entityTablePanel;
        this.entityTableModelProvider = entityTableModelProvider;
        putValue(SHORT_DESCRIPTION, "Edits selected entity");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTable entityTable = entityTablePanel.getTable();
        int[] selectedRows = entityTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (entityTable.isEditing()) {
            entityTable.getCellEditor().cancelCellEditing();
        }
        var entityTableModel = (EntityTableModel<T>) entityTable.getModel();
        int modelRow = entityTable.convertRowIndexToModel(selectedRows[0]);
        var entity = entityTableModel.getEntity(modelRow);

        EntityDialog<T> dialog = entityTablePanel.createDialog(entity, entityTableModelProvider);
        dialog.configureReadOnlyMode();
        dialog.showForView(entityTable, "View Entity");
    }

    public void setCurrentTablePanel(EntityTablePanel<T> panel) {
        this.entityTablePanel = panel;
    }
}
