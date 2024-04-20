package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeIngredientAmountEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link Repository} for {@link RecipeIngredientAmount} entity using SQL database.
 *
 * @author Vojtech Sassmann
 */
public class RecipeIngredientAmountSqlRepository implements Repository<RecipeIngredientAmount> {

    private final DataAccessObject<RecipeIngredientAmountEntity> recipeRecipeIngredientAmountAmountDao;
    private final EntityMapper<RecipeIngredientAmountEntity, RecipeIngredientAmount> recipeRecipeIngredientAmountAmountMapper;

    public RecipeIngredientAmountSqlRepository(
            DataAccessObject<RecipeIngredientAmountEntity> recipeRecipeIngredientAmountAmountDao,
            EntityMapper<RecipeIngredientAmountEntity, RecipeIngredientAmount> recipeRecipeIngredientAmountAmountMapper) {
        this.recipeRecipeIngredientAmountAmountDao = recipeRecipeIngredientAmountAmountDao;
        this.recipeRecipeIngredientAmountAmountMapper = recipeRecipeIngredientAmountAmountMapper;
    }


    @Override
    public List<RecipeIngredientAmount> findAll() {
        return recipeRecipeIngredientAmountAmountDao
                .findAll()
                .stream()
                .map(recipeRecipeIngredientAmountAmountMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(RecipeIngredientAmount newEntity) {
        recipeRecipeIngredientAmountAmountDao.create(recipeRecipeIngredientAmountAmountMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(RecipeIngredientAmount entity) {
        var existingRecipeIngredientAmount = recipeRecipeIngredientAmountAmountDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("RecipeIngredientAmount not found, guid: " + entity.getGuid()));
        var updatedRecipeIngredientAmountEntity = recipeRecipeIngredientAmountAmountMapper
                .mapExistingEntityToDatabase(entity, existingRecipeIngredientAmount.id());

        recipeRecipeIngredientAmountAmountDao.update(updatedRecipeIngredientAmountEntity);
    }

    @Override
    public void deleteByGuid(String guid) {
        recipeRecipeIngredientAmountAmountDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        recipeRecipeIngredientAmountAmountDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return recipeRecipeIngredientAmountAmountDao.existsByGuid(guid);
    }

    @Override
    public Optional<RecipeIngredientAmount> findByGuid(String guid) {
        return recipeRecipeIngredientAmountAmountDao
                .findByGuid(guid)
                .map(recipeRecipeIngredientAmountAmountMapper::mapToBusiness);
    }
}
