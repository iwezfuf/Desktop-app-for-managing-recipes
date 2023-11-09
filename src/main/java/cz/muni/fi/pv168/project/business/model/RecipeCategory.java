package cz.muni.fi.pv168.project.business.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recipe category.
 *
 * @author Marek Eibel
 */
public class RecipeCategory extends Entity {
    private final int id;
    private String name;
    private Color color;
    private static List<RecipeCategory> listOfCategories = new ArrayList<>();
    private static int idCounter = 0;

    public RecipeCategory(String categoryName, Color categoryColor) {
        this.id = idCounter++;
        this.name = categoryName;
        this.color = categoryColor;
        if (listOfCategories == null) {
            listOfCategories = new ArrayList<>();
        }
        listOfCategories.add(this);
    }

    public RecipeCategory() {
        this.id = idCounter++;
        this.name = "";
        this.color = Color.black;
        if (listOfCategories == null) {
            listOfCategories = new ArrayList<>();
        }
        listOfCategories.add(this);
    }

    public static List<RecipeCategory> getListOfCategories() {
        return listOfCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getGuid() {
        return "recipeCategory-" + id;
    }
}
