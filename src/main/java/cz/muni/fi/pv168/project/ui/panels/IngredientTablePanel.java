package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import java.util.function.Consumer;

public class IngredientTablePanel extends EntityTablePanel<Ingredient> {
    public IngredientTablePanel(EntityTableModel<Ingredient> entityTableModel, Validator<Ingredient> ingredientValidator, Class<? extends EntityDialog<Ingredient>> ingredientDialog, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, Ingredient.class, ingredientValidator, ingredientDialog, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Ingredient> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }
}
