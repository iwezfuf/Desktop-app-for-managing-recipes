package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeCategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeIngredientAmountEntity;

import java.util.ArrayList;

/**
 * Mapper from the {@link RecipeEntity} to {@link Recipe}.
 */
public class RecipeMapper implements EntityMapper<RecipeEntity, Recipe> {

    private final DataAccessObject<RecipeIngredientAmountEntity> recipeIngredientAmountDao;
    private final EntityMapper<RecipeIngredientAmountEntity, RecipeIngredientAmount> recipeIngredientAmountMapper;
    private final DataAccessObject<RecipeCategoryEntity> recipeCategoryDao;
    private final EntityMapper<RecipeCategoryEntity, RecipeCategory> recipeCategoryMapper;


    public RecipeMapper(
            DataAccessObject<RecipeIngredientAmountEntity> recipeIngredientAmountDao, EntityMapper<RecipeIngredientAmountEntity, RecipeIngredientAmount> recipeIngredientAmountMapper, DataAccessObject<RecipeCategoryEntity> recipeCategoryDao, EntityMapper<RecipeCategoryEntity, RecipeCategory> recipeCategoryMapper) {
        this.recipeIngredientAmountDao = recipeIngredientAmountDao;
        this.recipeIngredientAmountMapper = recipeIngredientAmountMapper;
        this.recipeCategoryDao = recipeCategoryDao;
        this.recipeCategoryMapper = recipeCategoryMapper;
    }

    @Override
    public Recipe mapToBusiness(RecipeEntity entity) {
        var recipeIngredientAmount = recipeIngredientAmountDao
                .findById(entity.recipeIngredientAmountId())
                .map(recipeIngredientAmountMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("RecipeIngredientAmount not found, id: " +
                        entity.recipeIngredientAmountId()));

        var category = recipeCategoryDao
                .findById(entity.recipeCategoryId())
                .map(recipeCategoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("RecipeCategory not found, id: " +
                        entity.recipeCategoryId()));

        return new Recipe(
                entity.guid(),
                entity.name(),
                entity.description(),
                entity.preparationTime(),
                entity.numOfServings(),
                entity.instructions(),
                category,
                new ArrayList<>() // TODO
//                recipeIngredientAmount
        );
    }

    @Override
    public RecipeEntity mapNewEntityToDatabase(Recipe entity) {
        var category = recipeCategoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("RecipeCategory not found, guid: " +
                        entity.getCategory().getGuid()));

        return new RecipeEntity(
                entity.getGuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getPreparationTime(),
                entity.getNumOfServings(),
                entity.getInstructions(),
                category.id(),
                0L // TODO
        );
    }

    @Override
    public RecipeEntity mapExistingEntityToDatabase(Recipe entity, Long dbId) {
        var category = recipeCategoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("RecipeCategory not found, guid: " +
                        entity.getCategory().getGuid()));

        return new RecipeEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getDescription(),
                entity.getPreparationTime(),
                entity.getNumOfServings(),
                entity.getInstructions(),
                category.id(),
                0L // TODO
        );
    }
}
