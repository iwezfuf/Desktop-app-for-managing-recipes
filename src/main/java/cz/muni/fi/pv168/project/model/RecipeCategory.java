package cz.muni.fi.pv168.project.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Marek Eibel
 */
public class RecipeCategory { // TODO probably must be reimplement it

    private String categoryName;
    private Color categoryColor;
    public static final List<RecipeCategory> listOfCategories = new ArrayList<>(
            List.of(new RecipeCategory("Meat", Color.RED),
                    new RecipeCategory("Vegetables", Color.GREEN)
            ));

    public RecipeCategory(String categoryName, Color categoryColor) {
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public static List<RecipeCategory> getListOfCategories() {
        return Collections.unmodifiableList(listOfCategories);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Color getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(Color categoryColor) {
        this.categoryColor = categoryColor;
    }

    @Override
    public String toString() {
        return getCategoryName();
    }
}
