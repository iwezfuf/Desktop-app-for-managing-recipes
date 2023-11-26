package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.panels.filter.RecipeFilterPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.function.Consumer;

public class RecipeTablePanel extends EntityTablePanelSidePanel<Recipe> {

    private RecipeFilterPanel recipeFilterPanel;

    public RecipeTablePanel(EntityTableModel<Recipe> entityTableModel, Validator<Recipe> recipeValidator, Class<? extends EntityDialog<Recipe>> recipeDialog, Consumer<Integer> onSelectionChange, EntityTableModelProvider etmp) {
        super(entityTableModel, Recipe.class, recipeValidator, recipeDialog, onSelectionChange, etmp);

        this.recipeFilterPanel = new RecipeFilterPanel(this);
        this.getSideScrollPane().setViewportView(recipeFilterPanel);
    }

    @Override
    protected void customizeTable(JTable table) {
        table.setDefaultRenderer(Object.class, new ColoredRowRenderer());
    }

    public RecipeFilterPanel getRecipeFilterPanel() {
        return recipeFilterPanel;
    }

    private static class ColoredRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            RecipeCategory recipeCategory = (RecipeCategory) table.getValueAt(row, 4);
            rendererComponent.setBackground(recipeCategory.getColor());
            return rendererComponent;
        }
    }
}
