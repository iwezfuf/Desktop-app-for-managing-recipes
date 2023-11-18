package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class IngredientTablePanel extends EntityTablePanel<Ingredient> {
    public IngredientTablePanel(EntityTableModel<Ingredient> entityTableModel, Validator<Ingredient> ingredientValidator, Class<? extends EntityDialog<Ingredient>> ingredientDialog, Consumer<Integer> onSelectionChange, int frameHeight) {
        super(entityTableModel, Ingredient.class, ingredientValidator, ingredientDialog, onSelectionChange, frameHeight);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Ingredient> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

    protected JPanel setUpTableWithSidePanel(EntityTableModel<Ingredient> entityTableModel, int frameHeight) {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel sidePanel = createSidePanel(frameHeight);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createSidePanel(int frameHeight) {

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(175, frameHeight));
        return sidePanel;
    }
}
