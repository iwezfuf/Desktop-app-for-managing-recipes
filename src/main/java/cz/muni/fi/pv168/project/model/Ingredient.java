package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.model.IngredientTableComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Ingredient extends AbstractUserItemData {
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
        return this.name + " [" + this.unit + "]";
    }
}
