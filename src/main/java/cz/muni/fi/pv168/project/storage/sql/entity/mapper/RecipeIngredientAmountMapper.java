package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeIngredientAmountEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;

/**
 * Mapper from the {@link RecipeIngredientAmountEntity} to {@link RecipeIngredientAmount}.
 */
public class RecipeIngredientAmountMapper implements EntityMapper<RecipeIngredientAmountEntity, RecipeIngredientAmount> {

    private final DataAccessObject<IngredientEntity> ingredientDao;
    private final EntityMapper<IngredientEntity, Ingredient> ingredientMapper;
    private final DataAccessObject<RecipeEntity> recipeDao;
    private final EntityMapper<RecipeEntity, Recipe> recipeMapper;

    public RecipeIngredientAmountMapper(
            DataAccessObject<IngredientEntity> ingredientDao, EntityMapper<IngredientEntity, Ingredient> ingredientMapper, DataAccessObject<RecipeEntity> recipeDao, EntityMapper<RecipeEntity, Recipe> recipeMapper) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
        this.recipeDao = recipeDao;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public RecipeIngredientAmount mapToBusiness(RecipeIngredientAmountEntity entity) {
        var ingredient = ingredientDao
                .findById(entity.ingredientId())
                .map(ingredientMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Ingredient not found, id: " +
                        entity.ingredientId()));

        var recipe = recipeDao
                .findById(entity.recipeId())
                .map(recipeMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Recipe not found, id: " +
                        entity.recipeId()));

        return new RecipeIngredientAmount(
                entity.guid(),
                recipe,
                ingredient,
                entity.amount()
        );
    }

    public RecipeIngredientAmount mapToBusinessWithRecipe(RecipeIngredientAmountEntity entity, Recipe recipe) {
        var ingredient = ingredientDao
                .findById(entity.ingredientId())
                .map(ingredientMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Ingredient not found, id: " +
                        entity.ingredientId()));

        return new RecipeIngredientAmount(
                entity.guid(),
                recipe,
                ingredient,
                entity.amount()
        );
    }

    @Override
    public RecipeIngredientAmountEntity mapNewEntityToDatabase(RecipeIngredientAmount entity) {
        var ingredient = ingredientDao
                .findByGuid(entity.getIngredient().getGuid())
                .orElseThrow(() -> new DataStorageException("Ingredient not found, guid: " +
                        entity.getIngredient().getGuid()));

        long recipeId = 0;
        if (entity.getRecipe() != null) {
            recipeId = recipeDao
                    .findByGuid(entity.getRecipe().getGuid())
                    .orElseThrow(() -> new DataStorageException("Recipe not found, guid: " +
                            entity.getRecipe().getGuid())).id();
        }

        return new RecipeIngredientAmountEntity(
                entity.getGuid(),
                recipeId,
                ingredient.id(),
                entity.getAmount()
        );
    }

    @Override
    public RecipeIngredientAmountEntity mapExistingEntityToDatabase(RecipeIngredientAmount entity, Long dbId) {
        var ingredient = ingredientDao
                .findByGuid(entity.getIngredient().getGuid())
                .orElseThrow(() -> new DataStorageException("Ingredient not found, guid: " +
                        entity.getIngredient().getGuid()));

        long recipeId = 0;
        if (entity.getRecipe() != null) {
            recipeId = recipeDao
                    .findByGuid(entity.getRecipe().getGuid())
                    .orElseThrow(() -> new DataStorageException("Recipe not found, guid: " +
                            entity.getRecipe().getGuid())).id();
        }

        return new RecipeIngredientAmountEntity(
                dbId,
                entity.getGuid(),
                recipeId,
                ingredient.id(),
                entity.getAmount()
        );
    }
}
