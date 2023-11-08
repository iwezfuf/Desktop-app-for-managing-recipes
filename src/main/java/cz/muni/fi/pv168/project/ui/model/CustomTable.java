package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing a custom table where individual rows are represented by
 * own custom classes.
 *
 * @author Marek Eibel
 */
public class CustomTable<T extends Entity> extends JTable {

    private DefaultTableModel model;
    private final String name;

    private TableCellEditor editor;
    private TableCellRenderer renderer;
    private int editingRow = -1;
    private int editingColumn = -1;

    private ListSelectionModel selectionModel;

    private int rowHeight;

    private Class<T> typeParameterClass;
    private CrudService<T> crudService;

    private List<T> dataList;

    /**
     * Creates a new CustomTable.
     *
     * @param tableName name of the table
     * @param editor editor for editing table cells (called components)
     * @param renderer renderer to render table cells
     */
    public CustomTable(String tableName, TableCellEditor editor, TableCellRenderer renderer, Class<T> typeParameterClass, CrudService<T> crudService) {
        this.name = tableName;
        this.editor = editor;
        this.renderer = renderer;
        this.rowHeight = 80;

        initModel();
        designTable();
        addMouseListener(new DoubleClickListener());

        // Initialize the selection model for the table
        selectionModel = getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Add a key listener for the "Delete" key
        addKeyListener(new DeleteKeyListener());


        this.typeParameterClass = typeParameterClass;
        this.crudService = crudService;
        dataList = new ArrayList<>(crudService.findAll());
    }

    /**
     * Creates a new CustomTable.
     *
     * @param tableName name of the table
     * @param editor editor for editing table cells (called components)
     * @param renderer renderer to render table cells
     * @param rowHeight height of one row (component)
     */
    public CustomTable(String tableName, TableCellEditor editor, TableCellRenderer renderer, Class<T> typeParameterClass, CrudService<T> crudService , int rowHeight) {
        this(tableName, editor, renderer, typeParameterClass, crudService);
        this.rowHeight = rowHeight;
    }

    private void designTable() {

        final int rowMargin = 20;
        final int columnMargin = 32;

        getColumnModel().setColumnMargin(columnMargin);
        setRowHeight(rowHeight);
        setRowMargin(rowMargin);
    }

    private void initModel() {

        final int INITIAL_COLUMNS_NUMBER = 1;
        final int INITIAL_ROWS_NUMBER = 0;

        model = new DefaultTableModel(INITIAL_ROWS_NUMBER, INITIAL_COLUMNS_NUMBER);
        model.setColumnIdentifiers(new String[]{name});
        setModel(model);

        // Set your custom cell renderer
        getColumnModel().getColumn(0).setCellRenderer(renderer);

        // Use the new cell renderer to highlight selected cells
        getColumnModel().getColumn(0).setCellRenderer(new CellRendererExtension(renderer));

        getColumnModel().getColumn(0).setCellEditor(editor);
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
        crudService.create(data);
        dataList.add(data);
    }

    /**
     * Gets the name of the table.
     *
     * @return name of the table
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return row == editingRow && column == editingColumn;
    }

    private class DoubleClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = rowAtPoint(e.getPoint());
            int column = columnAtPoint(e.getPoint());
            if (e.getClickCount() == 2) {
                editCell(row, column);
                clearSelection();
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem editItem = new JMenuItem("Edit");
                JMenuItem deleteItem = new JMenuItem("Delete");

                editItem.addActionListener(new EditAction((CustomTable<? extends Entity>) CustomTable.this));
                deleteItem.addActionListener(new DeleteAction((CustomTable<? extends Entity>) CustomTable.this));

                popupMenu.add(editItem);
                popupMenu.add(deleteItem);

                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    /**
     * Deletes the selected cells from the table.
     */
    public void deleteSelectedCells() {
        int[] selectedRows = getSelectedRows();
        Arrays.sort(selectedRows);

        if (selectedRows.length == 0) {
            new JOptionPane().showMessageDialog(null, "Please select at least one item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rowsDeleted = 0;
        for (int row : selectedRows) {
            if (row >= 0) {
                T entityToDelete = (T) model.getValueAt(row - rowsDeleted, 0);
                dataList.remove(entityToDelete);
                crudService.deleteByGuid(entityToDelete.getGuid());
                model.removeRow(row - rowsDeleted);
                rowsDeleted++;
            }
        }

        // Clear the selection
        clearSelection();
    }

    public void editSelectedCell() {
        if (selectionModel.getSelectedItemsCount() != 1) {
            new JOptionPane().showMessageDialog(null, "Please select exactly one item to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        editCell(getSelectedRow(), getSelectedColumn());
        crudService.update((T) model.getValueAt(getSelectedRow(), getSelectedColumn()));
        clearSelection();
    }

    public void editCell(int x, int y) {
        editingRow = x;
        editingColumn = y;
        editCellAt(x, y);
        Component editorComponent = getEditorComponent();
        if (editorComponent != null) {
            editorComponent.requestFocusInWindow();
        }
        editingRow = -1;
        editingColumn = -1;
    }

    private class DeleteKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                deleteSelectedCells();
            }
        }
    }

    @Override
    public int getRowHeight() {
        return rowHeight;
    }

    @Override
    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public Class<T> getTypeParameterClass() {
        return typeParameterClass;
    }
}
