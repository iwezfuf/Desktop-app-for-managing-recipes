package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.function.Consumer;

public class RecipeCategoryTablePanel extends EntityTablePanel<RecipeCategory> {
    public RecipeCategoryTablePanel(EntityTableModel<RecipeCategory> entityTableModel, Validator<RecipeCategory> recipeCategoryValidator, Class<? extends EntityDialog<RecipeCategory>> recipeCategoryDialog, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, RecipeCategory.class, recipeCategoryValidator, recipeCategoryDialog, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<RecipeCategory> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setDefaultRenderer(Object.class, new ColoredRowRenderer());
        return table;
    }

    @Override
    protected JPanel setUpTableWithSidePanel(EntityTableModel<RecipeCategory> entityTableModel, int frameHeight) {
        return null;
    }

    private static class ColoredRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Color color = (Color) table.getValueAt(row, 1);
            rendererComponent.setBackground(color);

            return rendererComponent;
        }
    }
}
