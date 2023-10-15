package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellRendererExtension implements TableCellRenderer {
    private final TableCellRenderer delegate;

    public CellRendererExtension(TableCellRenderer delegate) {
        this.delegate = delegate;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // Use the delegate renderer to render the cell
        Component c = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            // Set the opacity to 50%
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(true);
            }
            c.setBackground(new Color(c.getBackground().getRed(), c.getBackground().getGreen(), c.getBackground().getBlue(), 128)); // 128 is 50% opacity
        }

        return c;
    }
}
