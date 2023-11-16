package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import java.util.function.Consumer;

public class RecipeTablePanel extends EntityTablePanel<Recipe> {
    public RecipeTablePanel(EntityTableModel<Recipe> entityTableModel, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Recipe> entityTableModel) {
        var table = new JTable(entityTableModel);
        return table;
    }
}
