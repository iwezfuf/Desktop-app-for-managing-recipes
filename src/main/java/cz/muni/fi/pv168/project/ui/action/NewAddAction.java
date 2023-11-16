package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.panels.EntityTablePanel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

public final class NewAddAction<T extends Entity> extends AbstractAction {

    private final EntityTablePanel<T> entityTablePanel;
    private final ListModel<Department> departmentListModel;

    public NewAddAction(
            EntityTablePanel<T> entityTablePanel,
            ListModel<Department> departmentListModel) {
        super("Add", Icons.ADD_ICON);
        this.entityTablePanel = entityTablePanel;
        this.departmentListModel = departmentListModel;
        putValue(SHORT_DESCRIPTION, "Adds new entity");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var entityTableModel = entityTablePanel.getEntityTableModel();
        EntityDialog<T> dialog;
        Class<T> type = entityTablePanel.getType();
        try {
            T entityInstance = type.getConstructor().newInstance();
            dialog = entityTablePanel.getEntityDialog().getConstructor(
                    type, ListModel.class, Validator.class
            ).newInstance(
                    entityInstance,
                    departmentListModel,
                    entityTablePanel.getEntityValidator());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException("Failed to create dialog.", ex);
        }
        dialog.show(entityTablePanel.getTable(), "Add Entity")
                .ifPresent(entityTableModel::addRow);
    }
}
