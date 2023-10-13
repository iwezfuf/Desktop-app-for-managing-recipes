package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RecipeCellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        return new RecipeTableComponent((Recipe) value);
    }
}
