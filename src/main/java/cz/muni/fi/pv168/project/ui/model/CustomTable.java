package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author Marek Eibel
 */
public class CustomTable extends JTable {

    public CustomTable() {
        DefaultTableModel model = new DefaultTableModel(0, 1); // 0 rows initially
        setModel(model);
        getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer());
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
}
