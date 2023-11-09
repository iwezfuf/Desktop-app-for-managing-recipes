package cz.muni.fi.pv168.project.business.model;

import java.util.ArrayList;
import java.util.List;

public class Ingredient extends Entity {
    private final int ingredientId;
    private String name;

    private int nutritionalValue;
    private Unit unit;
    private static int idCounter = 0;

    private static List<Ingredient> listOfIngredients = new ArrayList<>();

    public Ingredient(String name, int nutritionalValue, Unit unit) {
        this.ingredientId = idCounter++;
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.unit = unit;
        if (listOfIngredients == null) {
            listOfIngredients = new ArrayList<>();
        }
        listOfIngredients.add(this);
    }

    public Ingredient() {
        this.ingredientId = idCounter++;
        this.name = "";
        this.nutritionalValue = 0;
        this.unit = null;
        if (listOfIngredients == null) {
            listOfIngredients = new ArrayList<>();
        }
        listOfIngredients.add(this);
    }

    public static List<Ingredient> getListOfIngredients() {
        return listOfIngredients;
    }

    public int getIngredientId() {
        return ingredientId;
    }

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
        return this.name + " [" + this.unit.getAbbreviation() + "]";
    }

    public int getRecipeCount(List<Recipe> recipes) {
        int count = 0;
        for (Recipe recipe : recipes) {
            if (recipe.getIngredientAmount(this) != null) {
                count++;
            }
        }
        return count;
    }

    public int getRecipeCountPercentage(List<Recipe> recipes) {
        return (int) Math.round((double) getRecipeCount(recipes) / recipes.size() * 100);
    }

    @Override
    public String getGuid() {
        return "ingredient-" + ingredientId;
    }

    public static boolean isInIngredient(Unit unit) {
        for (Ingredient ingredient : getListOfIngredients()) {
            if (ingredient.getUnit() == unit || ingredient.getUnit().getConversionUnit() == unit) {
                return true;
            }
        }
        return false;
    }
}
