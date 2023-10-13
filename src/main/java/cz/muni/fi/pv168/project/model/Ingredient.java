package cz.muni.fi.pv168.project.model;

import java.util.Set;

public class Ingredient {
    private final int ingredientId;
    private String name;

    private int nutritionalValue;
    private Unit unit;
    private static int idCounter = 0;

    public Ingredient(String name, int nutritionalValue, Unit unit) {
        this.ingredientId = idCounter++;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
