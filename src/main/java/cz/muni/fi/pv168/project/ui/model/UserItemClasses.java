package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.ui.dialog.*;
import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeCategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UserItemClasses {
    static final Map<Class<? extends Entity>, Class<? extends Component>> componentMap = new HashMap<>();
    public static final Map<Class<? extends Entity>, Class<? extends EntityDialog>> dialogMap = new HashMap<>();
    public static final Map<Class<? extends  Entity>, Class<? extends EntityDialog>> filterDialogMap = new HashMap<>();

static {
    componentMap.put(Recipe.class, RecipeTableComponent.class);
    componentMap.put(Ingredient.class, IngredientTableComponent.class);
    componentMap.put(Unit.class, UnitTableComponent.class);
    componentMap.put(RecipeCategory.class, RecipeCategoryTableComponent.class);

    dialogMap.put(Recipe.class, RecipeDialog.class);
    dialogMap.put(Ingredient.class, IngredientDialog.class);
    dialogMap.put(Unit.class, UnitDialog.class);
    dialogMap.put(RecipeCategory.class, RecipeCategoryDialog.class);

    filterDialogMap.put(Recipe.class, RecipeFilterDialog.class);
    filterDialogMap.put(Ingredient.class, IngredientFilterDialog.class);
    filterDialogMap.put(Unit.class, null); // without filter
    filterDialogMap.put(RecipeCategory.class, null);

    }
}
