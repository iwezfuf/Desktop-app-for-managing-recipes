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
        this.setText("<html><font color='"
                + "#" + Integer.toHexString(recipeCategory.getColor().getRGB()).substring(2)
                +  "'>■</font> "
                + recipeCategory.getName() + "</html>");
        this.setBackground(table.getBackground());

        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
            this.setText("<html><font color='"
                + "#" + Integer.toHexString(recipeCategory.getColor().getRGB()).substring(2)
                +  "'>■</font> "
                + "<font color=white>" + recipeCategory.getName() + "</font></html>");
        }
        return this;
    }
}
