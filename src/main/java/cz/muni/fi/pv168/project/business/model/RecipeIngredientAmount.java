package cz.muni.fi.pv168.project.business.model;

public class RecipeIngredientAmount extends Entity {
    private Ingredient ingredient;
    private int amount;

    public RecipeIngredientAmount(String guid, Ingredient ingredient, int amount) {
        super(guid);
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
}
