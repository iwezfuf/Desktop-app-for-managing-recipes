package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.panels.filter.RecipeFilterPanel;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;

import java.util.function.Consumer;

public class RecipeTablePanel extends EntityTablePanelSidePanel<Recipe> {

    private final RecipeFilterPanel recipeFilterPanel;

    public RecipeTablePanel(EntityTableModel<Recipe> entityTableModel, Validator<Recipe> recipeValidator, Class<? extends EntityDialog<Recipe>> recipeDialog, Consumer<Integer> onSelectionChange, EntityTableModelProviderWithCrud etmp) {
        super(entityTableModel, Recipe.class, recipeValidator, recipeDialog, onSelectionChange, etmp);

        this.recipeFilterPanel = new RecipeFilterPanel(this);
        this.getSideScrollPane().setViewportView(recipeFilterPanel);
    }
}
