package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RecipeCategoryRenderer extends JLabel implements TableCellRenderer {

    public RecipeCategoryRenderer() {
        this.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object recCategory,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        RecipeCategory recipeCategory = (RecipeCategory) recCategory;
        this.setText(recipeCategory.getName());
        this.setBackground(recipeCategory.getColor());

        return this;
    }
}
