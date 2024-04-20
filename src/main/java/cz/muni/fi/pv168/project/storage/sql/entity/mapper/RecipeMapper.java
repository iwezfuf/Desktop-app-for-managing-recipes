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
    private RecipeIngredientAmountMapper recipeIngredientAmountMapper;
    private final DataAccessObject<RecipeCategoryEntity> recipeCategoryDao;
    private final EntityMapper<RecipeCategoryEntity, RecipeCategory> recipeCategoryMapper;


    public RecipeMapper(
            DataAccessObject<RecipeIngredientAmountEntity> recipeIngredientAmountDao, RecipeIngredientAmountMapper recipeIngredientAmountMapper, DataAccessObject<RecipeCategoryEntity> recipeCategoryDao, EntityMapper<RecipeCategoryEntity, RecipeCategory> recipeCategoryMapper) {
        this.recipeIngredientAmountDao = recipeIngredientAmountDao;
        this.recipeIngredientAmountMapper = recipeIngredientAmountMapper;
        this.recipeCategoryDao = recipeCategoryDao;
        this.recipeCategoryMapper = recipeCategoryMapper;
    }

    @Override
    public Recipe mapToBusiness(RecipeEntity entity) {
        var category = recipeCategoryDao
                .findById(entity.recipeCategoryId())
                .map(recipeCategoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("RecipeCategory not found, id: " +
                        entity.recipeCategoryId()));

        ArrayList<RecipeIngredientAmount> recipeIngredientAmounts = new ArrayList<>();
        Recipe recipe = new Recipe(
                entity.guid(),
                entity.name(),
                entity.description(),
                entity.preparationTime(),
                entity.numOfServings(),
                entity.instructions(),
                category,
                recipeIngredientAmounts
        );

        for (RecipeIngredientAmountEntity recipeIngredientAmountEntity : recipeIngredientAmountDao.findAll()) {
            if (recipeIngredientAmountEntity.recipeId() == entity.id()) {
                recipeIngredientAmounts.add(recipeIngredientAmountMapper.mapToBusinessWithRecipe(recipeIngredientAmountEntity, recipe));
            }
        }

        return recipe;
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
                category.id()
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
                category.id()
        );
    }

    public void setRecipeIngredientAmountMapper(RecipeIngredientAmountMapper recipeIngredientAmountMapper) {
        this.recipeIngredientAmountMapper = recipeIngredientAmountMapper;
    }
}
