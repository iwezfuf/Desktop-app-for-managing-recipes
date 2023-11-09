package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class CellEditor extends AbstractCellEditor implements TableCellEditor {
    private Entity currentValue;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        EntityDialog<Entity> dialog;

        currentValue = (Entity) value;
        Class<?> valueClass = value.getClass();
        Class<? extends EntityDialog> dialogClass = UserItemClasses.dialogMap.get(valueClass);
        try {
            dialog = dialogClass.getConstructor(valueClass).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create dialog.", e);
        }

        dialog.show(null, "").ifPresentOrElse(r -> fireEditingStopped(), this::fireEditingStopped);

        Class<? extends Component> componentClass = UserItemClasses.componentMap.get(valueClass);
        try {
            return componentClass.getConstructor(valueClass).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create component.", e);
        }
    }

    @Override
    public Object getCellEditorValue() {
        return currentValue;
    }

}
