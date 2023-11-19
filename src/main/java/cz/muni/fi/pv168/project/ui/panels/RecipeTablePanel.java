package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.function.Consumer;

public class RecipeTablePanel extends EntityTablePanel<Recipe> {
    public RecipeTablePanel(EntityTableModel<Recipe> entityTableModel, Validator<Recipe> recipeValidator, Class<? extends EntityDialog<Recipe>> recipeDialog, Consumer<Integer> onSelectionChange, int frameHeight) {
        super(entityTableModel, Recipe.class, recipeValidator, recipeDialog, onSelectionChange, frameHeight);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Recipe> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

    protected JPanel setUpTableWithSidePanel(EntityTableModel<Recipe> entityTableModel, int frameHeight) {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel sidePanel = createSidePanel(frameHeight);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setDefaultRenderer(Object.class, new ColoredRowRenderer());
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createSidePanel(int frameHeight) {

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(175, frameHeight));
        return sidePanel;
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
