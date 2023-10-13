package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing custom table where individual rows are represented by
 * own custom classes.
 *
 * @author Marek Eibel
 */
public class CustomTable<T> extends JTable {

    private DefaultTableModel model;
    private final String name;

    private TableCellEditor editor;
    private TableCellRenderer renderer;

    /**
     * Creates new CustomTable.
     *
     * @param tableName name of the table
     */
    public CustomTable(String tableName, TableCellEditor editor, TableCellRenderer renderer) {

        this.name = tableName;
        this.editor = editor;
        this.renderer = renderer;
        initModel();
        designTable();
    }

    private void designTable() {

        final int rowHeight = 80;
        final int rowMargin = 20;

        setRowHeight(rowHeight);
        setRowMargin(rowMargin);
    }

    private void initModel() {

        final int INITIAL_COLUMNS_NUMBER = 1;
        final int INITIAL_ROWS_NUMBER = 0;

        model = new DefaultTableModel(INITIAL_ROWS_NUMBER, INITIAL_COLUMNS_NUMBER);
        model.setColumnIdentifiers(new String[]{name});
        setModel(model);
        getColumnModel().getColumn(0).setCellEditor(editor);
        getColumnModel().getColumn(0).setCellRenderer(renderer);
    }

    /**
     * Adds data to the table.
     *
     * @param data row to add
     */
    public void addData(T data) {
        List<T> lst = new ArrayList<>();
        lst.add(data);
        model.addRow(lst.toArray());
    }

    /**
     * Gets name of the table.
     *
     * @return name of the table
     */
    @Override
    public String getName() {
        return name;
    }
}
