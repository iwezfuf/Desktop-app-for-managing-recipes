package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class RecipeCellEditor extends AbstractCellEditor implements TableCellEditor {
    private Recipe currentRecipe;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRecipe = (Recipe) value;
        RecipeDialog dialog = new RecipeDialog(currentRecipe);
        dialog.show(null, "Edit recipe").ifPresentOrElse(r -> fireEditingStopped(), this::fireEditingStopped);

        return new RecipeTableComponent(currentRecipe);
    }

    @Override
    public Object getCellEditorValue() {
        return currentRecipe;
    }

}
