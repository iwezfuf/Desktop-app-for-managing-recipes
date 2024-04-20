package cz.muni.fi.pv168.project.business.model;

import java.awt.*;

/**
 * Represents a recipe category.
 *
 * @author Marek Eibel
 */
public class RecipeCategory extends Entity {
    private String name;
    private Color color;

    public RecipeCategory(String guid, String categoryName, Color categoryColor) {
        super(guid);
        this.name = categoryName;
        this.color = categoryColor;
    }

    public RecipeCategory(String categoryName, Color categoryColor) {
        this.name = categoryName;
        this.color = categoryColor;
    }

    public RecipeCategory() {
        this.name = "";
        this.color = Color.black;
    }

    @Override
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
