package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.AbstractUserItemData;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeCategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UserItemClasses {
    static final Map<Class<? extends AbstractUserItemData>, Class<? extends Component>> componentMap = new HashMap<>();
    static final Map<Class<? extends  AbstractUserItemData>, Class<? extends EntityDialog>> dialogMap = new HashMap<>();

static {
    componentMap.put(Recipe.class, RecipeTableComponent.class);
    componentMap.put(Ingredient.class, IngredientTableComponent.class);
    componentMap.put(Unit.class, UnitTableComponent.class);
    componentMap.put(RecipeCategory.class, RecipeCategoryTableComponent.class);

    dialogMap.put(Recipe.class, RecipeDialog.class);
    dialogMap.put(Ingredient.class, IngredientDialog.class);
    dialogMap.put(Unit.class, UnitDialog.class);
    dialogMap.put(RecipeCategory.class, RecipeCategoryDialog.class);
    }
}
