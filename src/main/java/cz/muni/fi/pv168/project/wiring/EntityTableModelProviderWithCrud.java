package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

public class EntityTableModelProviderWithCrud {
    private final EntityTableModel<Recipe> recipeTableModel;
    private final EntityTableModel<Ingredient> ingredientTableModel;
    private final EntityTableModel<Unit> unitTableModel;
    private final EntityTableModel<RecipeCategory> recipeCategoryTableModel;
    private final CrudService<RecipeIngredientAmount> ingredientAmountCrudService;

    public EntityTableModelProviderWithCrud(EntityTableModel<Recipe> recipeTableModel, EntityTableModel<Ingredient> ingredientTableModel, EntityTableModel<Unit> unitTableModel, EntityTableModel<RecipeCategory> recipeCategoryTableModel, CrudService<RecipeIngredientAmount> ingredientAmountCrudService) {
        this.recipeTableModel = recipeTableModel;
        this.ingredientTableModel = ingredientTableModel;
        this.unitTableModel = unitTableModel;
        this.recipeCategoryTableModel = recipeCategoryTableModel;
        this.ingredientAmountCrudService = ingredientAmountCrudService;
    }


    public EntityTableModel<Recipe> getRecipeTableModel() {
        return recipeTableModel;
    }

    public EntityTableModel<Ingredient> getIngredientTableModel() {
        return ingredientTableModel;
    }

    public EntityTableModel<Unit> getUnitTableModel() {
        return unitTableModel;
    }

    public EntityTableModel<RecipeCategory> getRecipeCategoryTableModel() {
        return recipeCategoryTableModel;
    }

    public CrudService<RecipeIngredientAmount> getIngredientAmountCrudService() {
        return ingredientAmountCrudService;
    }
}
