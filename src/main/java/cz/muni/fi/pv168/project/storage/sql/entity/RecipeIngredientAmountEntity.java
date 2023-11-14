package cz.muni.fi.pv168.project.storage.sql.entity;

public record RecipeIngredientAmountEntity(Long id, String guid, long recipeId, long ingredientId, int amount) {
    public RecipeIngredientAmountEntity(Long id, String guid, long recipeId, long ingredientId, int amount) {
        this.id = id;
        this.guid = guid;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
    }

    public RecipeIngredientAmountEntity(String guid, long recipeId, long ingredientId, int amount) {
        this(null, guid, recipeId, ingredientId, amount);
    }
}
