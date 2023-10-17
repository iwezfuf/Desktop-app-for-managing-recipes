package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.RecipeTableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Marek Eibel
 */
public class Recipe extends AbstractUserItemData {

    private String name;
    private String description;
    private final int id;
    private int preparationTime;
    private int numOfServings;
    private String instructions;
    private RecipeCategory category;
    private static int idCounter = 0;

    /**
     * Represents pairs ingredientId : ingredient amount
     */
    private Map<Ingredient, Integer> ingredients;
    private static List<Recipe> listOfRecipes = new ArrayList<>();

    public static List<Recipe> getListOfRecipes() {
        return listOfRecipes;
    }

    public Recipe(String name, String description, int preparationTime, int numOfServings,
                  String instructions, RecipeCategory category, Map<Ingredient, Integer> ingredients) {

        this.name = name;
        this.description = description;
        this.id = Recipe.idCounter++;
        this.preparationTime = preparationTime;
        this.numOfServings = numOfServings;
        this.instructions = instructions;
        this.category = category;
        this.ingredients = ingredients;
        if (listOfRecipes == null) {
            listOfRecipes = new ArrayList<>();
        }
        listOfRecipes.add(this);
    }

    public int getRecipeId() {
        return id;
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

    public void setCategory(RecipeCategory category) {
        this.category = category;
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

    public void addIngredient(Ingredient ingredient, int amount) {
        if (ingredients.containsKey(ingredient)) {
            ingredients.put(ingredient, ingredients.get(ingredient) + amount);
        } else {
            ingredients.put(ingredient, amount);
        }
    }

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.containsKey(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public Set<Map.Entry<Ingredient, Integer>> getIngredientAmountPairs() {
        return ingredients.entrySet();
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

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public static int getNumOfRecipes() {
        return listOfRecipes.size();
    }

}
