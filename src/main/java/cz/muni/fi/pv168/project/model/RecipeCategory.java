package cz.muni.fi.pv168.project.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marek Eibel
 */
public class RecipeCategory extends AbstractUserItemData { // TODO probably must be reimplement it
    private String name;
    private Color color;
    private static List<RecipeCategory> listOfCategories = new ArrayList<>();

    public RecipeCategory(String categoryName, Color categoryColor) {
        this.name = categoryName;
        this.color = categoryColor;
        if (listOfCategories == null) {
            listOfCategories = new ArrayList<>();
        }
        listOfCategories.add(this);
    }

    public static List<RecipeCategory> getListOfCategories() {
        return Collections.unmodifiableList(listOfCategories);
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

    @Override
    public String toString() {
        return getName();
    }
}
