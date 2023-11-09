package cz.muni.fi.pv168.project.business.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Set;
import cz.muni.fi.pv168.project.model.AbstractFilter;

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

    // private RowFilter<DefaultTableModel, Object> rowFilter;

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
        return recipe.getIngredientsSet().containsAll(ingredientsInFilter) && evaluateRecipeCategoriesFilter(recipe)
                && preperationTimeRange.isValueInRange(recipe.getPreparationTime()) &&
                nutritionValueRange.isValueInRange(recipe.getNutritionalValue());
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
    public RowFilter<DefaultTableModel, Object> getRowFilter() {
        return new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ?> entry) {

                Object columnValue = entry.getValue(0);
                return evaluateRecipe((Recipe)columnValue);
            }
        };
    }
}
