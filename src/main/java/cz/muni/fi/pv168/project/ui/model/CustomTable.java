package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Class representing custom table where individual rows are represented by
 * own custom classes.
 *
 * @author Marek Eibel
 */
public class CustomTable extends JTable {

    private DefaultTableModel model;
    private final String name;

    /**
     * Creates new CustomTable.
     *
     * @param tableName name of the table
     */
    public CustomTable(String tableName) {

        this.name = tableName;
        initModel();
    }

    private void initModel() {

        int COLUMNS_NUMBER = 1;
        DefaultTableModel model = new DefaultTableModel(0, COLUMNS_NUMBER);
        model.setColumnIdentifiers(new String[]{name});
        setModel(model);
        getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer());
        this.model = model;
    }

    public void addComponent(AbstractTableComponent component) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(new Object[]{component});
    }

    private static class CustomCellRenderer extends JPanel implements TableCellRenderer {
        public CustomCellRenderer() {
            setLayout(new BorderLayout());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof AbstractTableComponent customComponent) {
                removeAll();
                add(customComponent, BorderLayout.CENTER);
                return this;
            }
            return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
