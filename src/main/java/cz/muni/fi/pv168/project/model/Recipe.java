package cz.muni.fi.pv168.project.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marek Eibel
 */
public class Recipe {

    private String name;
    private String description;
    private int recipeId;
    private int preparationTime;
    private int numOfServings;
    private String instructions;
    private RecipeCategory category;
    /**
     * Represents pairs ingredientId : ingredient amount
     */
    private Map<Ingredient, Integer> ingredients;

    public Recipe(int recipeId, String name, String description, int preparationTime, int numOfServings,
                  String instructions, RecipeCategory category, Map<Ingredient, Integer> ingredients) {

        this.name = name;
        this.description = description;
        this.recipeId = recipeId;
        this.preparationTime = preparationTime;
        this.numOfServings = numOfServings;
        this.instructions = instructions;
        this.category = category;
        this.ingredients = ingredients;
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

    public RecipeCategory getCategory() {
        return category;
    }

    public String getIngredients() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int amount = entry.getValue();
            result.append(ingredient.getName() + ": " + amount + "\n");
        }
        return result.toString();
    }

    public int getNutritionalValue() {
        int result = 0;
        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            int amount = entry.getValue();
            result += amount * ingredient.getNutritionalValue();
        }
        return result;
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

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
