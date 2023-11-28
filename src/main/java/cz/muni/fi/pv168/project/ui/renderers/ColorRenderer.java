package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColorRenderer extends JLabel implements TableCellRenderer {
    public ColorRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object color,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        Color newColor = (Color) color;
        setBackground(newColor);
        return this;
    }
}

