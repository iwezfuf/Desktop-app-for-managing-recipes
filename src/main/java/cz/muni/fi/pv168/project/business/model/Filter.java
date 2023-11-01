package cz.muni.fi.pv168.project.business.model;

import java.util.List;

/**
 * @author Marek Eibel
 */
public class Filter {

    private List<Recipe> recipesInFilter;
    private List<RecipeCategory> recipeCategoriesInFilter;
    private Range nutritionValueRange;
    private Range preperationTimeRange;

    public Filter(List<Recipe> recipesInFilter, List<RecipeCategory> recipeCategoriesInFilter, Range nutritionValueRange, Range preperationTimeRange) {
        this.recipesInFilter = recipesInFilter;
        this.recipeCategoriesInFilter = recipeCategoriesInFilter;
        this.nutritionValueRange = nutritionValueRange;
        this.preperationTimeRange = preperationTimeRange;
    }

    public List<Recipe> getRecipesInFilter() {
        return recipesInFilter;
    }

    public void setRecipesInFilter(List<Recipe> recipesInFilter) {
        this.recipesInFilter = recipesInFilter;
    }

    public List<RecipeCategory> getRecipeCategoriesInFilter() {
        return recipeCategoriesInFilter;
    }

    public void setRecipeCategoriesInFilter(List<RecipeCategory> recipeCategoriesInFilter) {
        this.recipeCategoriesInFilter = recipeCategoriesInFilter;
    }

    public Range getNutritionValueRange() {
        return nutritionValueRange;
    }

    public void setNutritionValueRange(Range nutritionValueRange) {
        this.nutritionValueRange = nutritionValueRange;
    }

    public Range getPreperationTimeRange() {
        return preperationTimeRange;
    }

    public void setPreperationTimeRange(Range preperationTimeRange) {
        this.preperationTimeRange = preperationTimeRange;
    }
}
