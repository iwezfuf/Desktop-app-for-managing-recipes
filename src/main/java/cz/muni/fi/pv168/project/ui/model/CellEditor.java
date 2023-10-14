package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.AbstractUserItemData;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeCategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class CellEditor extends AbstractCellEditor implements TableCellEditor {
    private AbstractUserItemData currentValue;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        EntityDialog dialog;
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (value.getClass().equals(Recipe.class)) {
            currentValue = (Recipe) value;
            dialog = new RecipeDialog((Recipe) currentValue);
        } else if (value.getClass().equals(Ingredient.class)) {
            currentValue = (Ingredient) value;
            dialog = new IngredientDialog((Ingredient) currentValue);
        } else if (value.getClass().equals(Unit.class)) {
            currentValue = (Unit) value;
            dialog = new UnitDialog((Unit) currentValue);
        } else if (value.getClass().equals(RecipeCategory.class)) {
            currentValue = (RecipeCategory) value;
            dialog = new RecipeCategoryDialog((RecipeCategory) currentValue);
        }
        else {
            throw new IllegalArgumentException("Unknown class of value: " + value.getClass());
        }
        dialog.show(null, "Edit").ifPresentOrElse(r -> fireEditingStopped(), this::fireEditingStopped);

        if (value.getClass().equals(Recipe.class)) {
            return new RecipeTableComponent((Recipe) currentValue);
        } else if (value.getClass().equals(Ingredient.class)) {
            return new IngredientTableComponent((Ingredient) currentValue);
        } else if (value.getClass().equals(Unit.class)) {
            return new UnitTableComponent((Unit) currentValue);
        } else if (value.getClass().equals(RecipeCategory.class)) {
            return new RecipeCategoryTableComponent((RecipeCategory) currentValue);
        }
        else {
            throw new IllegalArgumentException("Unknown class of value: " + value.getClass());
        }
    }

    @Override
    public Object getCellEditorValue() {
        return currentValue;
    }

}
