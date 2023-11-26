package cz.muni.fi.pv168.project.ui.filters;

import javax.swing.*;
import java.util.Set;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

/**
 * The RecipeFilter class represents a filter for recipes.
 *
 * This class provides a mechanism to filter recipes in a table, allowing users to specify criteria for ingredients,
 * recipe categories, nutritional value ranges, and preparation time ranges.
 *
 * @author Marek Eibel
 */
public class RecipeFilter implements AbstractFilter {

    private Set<Ingredient> ingredientsInFilter;
    private Set<RecipeCategory> recipeCategoriesInFilter;
    private Range nutritionValueRange;
    private Range preperationTimeRange;

    /**
     * Creates new RecipeFilter.
     *
     * @param ingredientsInFilter a list of ingredients to filter recipes by
     * @param recipeCategoriesInFilter a list of recipe categories to filter recipes by
     * @param nutritionValueRange range of nutritional values to filter recipes by
     * @param preperationTimeRange range of preparation times to filter recipes by
     */
    public RecipeFilter(Set<Ingredient> ingredientsInFilter, Set<RecipeCategory> recipeCategoriesInFilter, Range nutritionValueRange, Range preperationTimeRange) {
        this.ingredientsInFilter = ingredientsInFilter;
        this.recipeCategoriesInFilter = recipeCategoriesInFilter;
        this.nutritionValueRange = nutritionValueRange;
        this.preperationTimeRange = preperationTimeRange;
    }

    /**
     * Returns true if the given recipe should be left in the table.
     *
     * @param recipe recipe to evaluate
     * @return true if the given recipe should be left in the table else false
     */
    private boolean evaluateRecipe(Recipe recipe) {
        boolean containsAllIngredients = recipe.getIngredientsSet().containsAll(ingredientsInFilter);
        boolean containsAllCategories = evaluateRecipeCategoriesFilter(recipe);
        boolean isInTimeRange = preperationTimeRange.isValueInRange(recipe.getPreparationTime());
        boolean isInNutritionalValueRange = nutritionValueRange.isValueInRange(recipe.getNutritionalValue());
        return  containsAllIngredients && containsAllCategories
                && isInTimeRange && isInNutritionalValueRange;

    }

    private boolean evaluateRecipeCategoriesFilter(Recipe recipe) {
        if (recipeCategoriesInFilter.isEmpty()) {
            return true;
        }

        return recipeCategoriesInFilter.contains(recipe.getCategory());
    }

    public Set<Ingredient> getIngredientsInFilter() {
        return ingredientsInFilter;
    }

    public Set<RecipeCategory> getRecipeCategoriesInFilter() {
        return recipeCategoriesInFilter;
    }

    public Range getNutritionValueRange() {
        return nutritionValueRange;
    }

    public Range getPreperationTimeRange() {
        return preperationTimeRange;
    }

    @Override
    public RowFilter<EntityTableModel<Recipe>, Integer> getRowFilter() {
        return new RowFilter<EntityTableModel<Recipe>, Integer>() {
            @Override
            public boolean include(Entry<? extends EntityTableModel<Recipe>, ? extends Integer> entry) {
                Recipe row = entry.getModel().getEntity(entry.getIdentifier());
                return evaluateRecipe(row);
            }
        };
    }
}
