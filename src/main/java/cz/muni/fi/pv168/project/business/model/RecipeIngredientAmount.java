package cz.muni.fi.pv168.project.business.model;

public class RecipeIngredientAmount extends Entity {

    private final Ingredient ingredient;
    private int amount;
    private Recipe recipe;

    public RecipeIngredientAmount(String guid, Recipe recipe, Ingredient ingredient, int amount) {
        super(guid);
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RecipeIngredientAmount{" +
                "ingredient=" + ingredient +
                ", amount=" + amount +
                ", recipe=" + recipe +
                ", guid='" + guid + '\'' +
                '}';
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

    @Override
    public String getName() {
        return ingredient.getName();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
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
