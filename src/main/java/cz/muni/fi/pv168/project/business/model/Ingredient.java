package cz.muni.fi.pv168.project.business.model;

import java.util.Collection;
import java.util.List;

public class Ingredient extends Entity {
        private String name;
        private int nutritionalValue;
        private Unit unit;

    public Ingredient(String guid, String name, int nutritionalValue, Unit unit) {
        super(guid);
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.unit = unit;
    }

    public Ingredient(String name, int nutritionalValue, Unit unit) {
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.unit = unit;
    }


    public Ingredient() {
        this.name = "";
        this.nutritionalValue = 0;
        this.unit = null;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.name + " [" + this.unit.getAbbreviation() + " - " + this.nutritionalValue + " kcal]";
    }

    public int getRecipesCount(Collection<Recipe> allRecipes) {
        int count = 0;
        for (Recipe recipe : allRecipes) {
            for (RecipeIngredientAmount ingredientAmount : recipe.getIngredients()) {
                if (ingredientAmount.getIngredient().equals(this)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public String getRecipeCountPercentage(Collection<Recipe> recipes) {
        if (recipes.isEmpty()) {
            return "No recipes";
        }
        int count = getRecipesCount(recipes);
        return "Used in " + count + " (" + ((int) Math.round((double) getRecipesCount(recipes) / recipes.size() * 100) + "%) recipes");
    }

}
