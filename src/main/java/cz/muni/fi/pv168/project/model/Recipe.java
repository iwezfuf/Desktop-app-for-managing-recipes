package cz.muni.fi.pv168.project.model;

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
    private Set<RecipeCategory> categoryIds;
    private Set<Integer> ingredientIds;

    public Recipe(int recipeId, String name, String description, int preparationTime, int numOfServings,
                  String instructions, Set<RecipeCategory> categoryIds, Set<Integer> ingredientIds) {

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

    public Set<RecipeCategory> getCategoryIds() {
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

    public void setCategoryIds(Set<RecipeCategory> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public void addRecipeCategory(RecipeCategory category) {
        this.categoryIds.add(category);
    }

    public void setIngredientIds(Set<Integer> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", recipeId=" + recipeId +
                ", preparationTime=" + preparationTime +
                ", numOfServings=" + numOfServings +
                ", instructions='" + instructions + '\'' +
                ", categoryIds=" + categoryIds +
                ", ingredientIds=" + ingredientIds +
                '}';
    }
}
