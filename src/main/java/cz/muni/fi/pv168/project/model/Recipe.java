package cz.muni.fi.pv168.project.model;

import java.util.Set;

/**
 * @author Marek Eibel
 */
public class Recipe {

    private String name; // add ID
    private String description;
    private int recipeId;
    private int preparationTime;
    private int numOfServings;
    private String instructions;
    private Set<Integer> categoryIds;
    private Set<Integer> ingredientIds;



    public Recipe(int recipeId, String name, String description, int preparationTime, int numOfServings,
                  String instructions, Set<Integer> categoryIds, Set<Integer> ingredientIds) {
        this.name = name;
        this.description = description;
        this.recipeId = recipeId;
        this.preparationTime = preparationTime;
        this.numOfServings = numOfServings;
        this.instructions = instructions;
        this.categoryIds = categoryIds;
        this.ingredientIds = ingredientIds;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public int getNumOfServings() {
        return numOfServings;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getCategoryIds() {
        return categoryIds;
    }

    public Set<Integer> getIngredientIds() {
        return ingredientIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
