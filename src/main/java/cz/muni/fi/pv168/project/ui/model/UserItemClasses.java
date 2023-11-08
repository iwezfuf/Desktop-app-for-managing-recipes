package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.AbstractUserItemData;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UserItemClasses {
    static final Map<Class<? extends AbstractUserItemData>, Class<? extends Component>> componentMap = new HashMap<>();
    public static final Map<Class<? extends  AbstractUserItemData>, Class<? extends EntityDialog>> dialogMap = new HashMap<>();
    public static final Map<Class<? extends  AbstractUserItemData>, Class<? extends EntityDialog>> filterDialogMap = new HashMap<>();

    public static final Map<Class<? extends  AbstractUserItemData>, AbstractUserItemData> defaultValuesMap = new HashMap<>();

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
    filterDialogMap.put(Unit.class, RecipeFilterDialog.class);
    filterDialogMap.put(RecipeCategory.class, RecipeFilterDialog.class);

    var blankCategory = new RecipeCategory("", Color.BLACK);
    var blankUnit = new Unit("", null, -1, "");
    var blankIngredient = new Ingredient("", 0, blankUnit);
    var blankRecipe = new Recipe("", "", 0, 0,
            "", blankCategory, new HashMap<Ingredient, Integer>());
    defaultValuesMap.put(Recipe.class, blankRecipe);
    defaultValuesMap.put(RecipeCategory.class, blankCategory);
    defaultValuesMap.put(Ingredient.class, blankIngredient);
    defaultValuesMap.put(Unit.class, blankUnit);
    Ingredient.getListOfIngredients().remove(blankIngredient);
    Unit.getListOfUnits().remove(blankUnit);
    RecipeCategory.getListOfCategories().remove(blankCategory);
    }
}
