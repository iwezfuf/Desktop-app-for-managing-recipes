package cz.muni.fi.pv168.project.model;

import java.util.Set;

public class Ingredient {
    private int ingredientId;
    private String name;

    private int nutritionalValue;
    private Unit unit;

    public Ingredient(int ingredientId, String name, int nutritionalValue, Unit unit) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.unit = unit;
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
}
