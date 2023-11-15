package cz.muni.fi.pv168.project.business.model;

public class RecipeIngredientAmount extends Entity {
    private Ingredient ingredient;
    private int amount;
    private Recipe recipe;

    public RecipeIngredientAmount(String guid, Recipe recipe, Ingredient ingredient, int amount) {
        super(guid);
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public RecipeIngredientAmount(Recipe recipe, Ingredient ingredient, int amount) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public RecipeIngredientAmount() {
        this.ingredient = new Ingredient();
        this.amount = 0;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
