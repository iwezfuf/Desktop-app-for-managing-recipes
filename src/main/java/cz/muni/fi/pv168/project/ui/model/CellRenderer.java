package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (value.getClass().equals(Recipe.class)) {
            return new RecipeTableComponent((Recipe) value);
        } else if (value.getClass().equals(Ingredient.class)) {
            return new IngredientTableComponent((Ingredient) value);
        } else if (value.getClass().equals(Unit.class)) {
            return new UnitTableComponent((Unit) value);
        } else if (value.getClass().equals(RecipeCategory.class)) {
            return new RecipeCategoryTableComponent((RecipeCategory) value);
        } else {
            throw new IllegalArgumentException("Unknown class of value: " + value.getClass());
        }
    }
}
