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

        if (recCategory instanceof RecipeCategory) {
            RecipeCategory recipeCategory = (RecipeCategory) recCategory;

            JPanel panel = new JPanel();
            panel.setOpaque(true);
            panel.setLayout(new BorderLayout());

            JLabel colorLabel = new JLabel("   ");
            colorLabel.setOpaque(true);
            colorLabel.setBackground(recipeCategory.getColor());

            JLabel textLabel = new JLabel("  " + recipeCategory.getName());

            panel.add(colorLabel, BorderLayout.WEST);
            panel.add(textLabel, BorderLayout.CENTER);

            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
                colorLabel.setBackground(table.getSelectionBackground());
                textLabel.setForeground(table.getSelectionForeground());
            } else {
                panel.setBackground(table.getBackground());
                colorLabel.setBackground(recipeCategory.getColor());
                textLabel.setForeground(table.getForeground());
            }

            return panel;
        }

        return new JLabel(recCategory.toString());
    }
}
