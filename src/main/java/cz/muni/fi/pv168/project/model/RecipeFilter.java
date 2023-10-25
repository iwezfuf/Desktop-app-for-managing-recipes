package cz.muni.fi.pv168.project.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * The RecipeFilter class represents a filter for recipes.
 *
 * This class provides a mechanism to filter recipes in a table, allowing users to specify criteria for ingredients,
 * recipe categories, nutritional value ranges, and preparation time ranges.
 *
 * @author Marek Eibel
 */
public class RecipeFilter implements AbstractFilter {

    private List<Ingredient> ingredientsInFilter;
    private List<RecipeCategory> recipeCategoriesInFilter;
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
    public RecipeFilter(List<Ingredient> ingredientsInFilter, List<RecipeCategory> recipeCategoriesInFilter, Range nutritionValueRange, Range preperationTimeRange) {
        this.ingredientsInFilter = ingredientsInFilter;
        this.recipeCategoriesInFilter = recipeCategoriesInFilter;
        this.nutritionValueRange = nutritionValueRange;
        this.preperationTimeRange = preperationTimeRange;
    }

    // TODO generate setters & getters

    /**
     * Returns true if the given recipe should be left in the table.
     *
     * @param recipe recipe to evaluate
     * @return true if the given recipe should be left in the table else false
     */
    private boolean evaluateRecipe(Recipe recipe) {

        return preperationTimeRange.isValueInRange(recipe.getPreparationTime()) &&
                nutritionValueRange.isValueInRange(recipe.getNutritionalValue()); // TODO: check also ingredients and categories
                                                                                  // TODO: if the object is null, then do not check it
    }

    @Override
    public RowFilter<DefaultTableModel, Object> getRowFilter() {
        return new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {

                Object columnValue = entry.getValue(0); // TODO: think about where to set this column index
                return evaluateRecipe((Recipe)columnValue);
            }
        };
    }
}
