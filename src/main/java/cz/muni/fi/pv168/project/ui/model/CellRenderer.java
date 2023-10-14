package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Class<?> valueClass = value.getClass();
        Class<? extends Component> componentClass = UserItemClasses.componentMap.get(valueClass);

        try {
            return componentClass.getConstructor(valueClass).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create component.", e);
        }
    }
}
