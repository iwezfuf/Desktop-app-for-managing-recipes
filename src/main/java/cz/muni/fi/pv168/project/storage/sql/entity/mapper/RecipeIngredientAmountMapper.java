package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeIngredientAmountEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;

/**
 * Mapper from the {@link RecipeIngredientAmountEntity} to {@link RecipeIngredientAmount}.
 */
public class RecipeIngredientAmountMapper implements EntityMapper<RecipeIngredientAmountEntity, RecipeIngredientAmount> {

    private final DataAccessObject<IngredientEntity> ingredientDao;
    private final EntityMapper<IngredientEntity, Ingredient> ingredientMapper;

    public RecipeIngredientAmountMapper(
            DataAccessObject<IngredientEntity> ingredientDao, EntityMapper<IngredientEntity, Ingredient> ingredientMapper) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public RecipeIngredientAmount mapToBusiness(RecipeIngredientAmountEntity entity) {
        var ingredient = ingredientDao
                .findById(entity.ingredientId())
                .map(ingredientMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Ingredient not found, id: " +
                        entity.ingredientId()));

        return new RecipeIngredientAmount(
                entity.guid(),
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

        return new RecipeIngredientAmountEntity(
                entity.getGuid(),
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

        return new RecipeIngredientAmountEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getNutritionalValue(),
                ingredient.id()
        );
    }
}
