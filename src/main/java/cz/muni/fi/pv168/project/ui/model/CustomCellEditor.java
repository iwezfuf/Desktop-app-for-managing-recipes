package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class CustomCellEditor implements TableCellEditor {
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        System.out.println("getTableCellEditorComponent");
        return null;
    }

    @Override
    public Object getCellEditorValue() {
        System.out.println("getCellEditorValue");
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        System.out.println("isCellEditable");
        return false;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        System.out.println("shouldSelectCell");
        return false;
    }

    @Override
    public boolean stopCellEditing() {
        return false;
    }

    @Override
    public void cancelCellEditing() {

    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        System.out.println("addCellEditorListener");
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        System.out.println("removeCellEditorListener");
    }
}
