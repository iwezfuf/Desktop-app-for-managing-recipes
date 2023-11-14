package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeCategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link Repository} for {@link RecipeCategory} entity using SQL database.
 *
 * @author Vojtech Sassmann
 */
public class RecipeCategorySqlRepository implements Repository<RecipeCategory> {

    private final DataAccessObject<RecipeCategoryEntity> recipeCategoryDao;
    private final EntityMapper<RecipeCategoryEntity, RecipeCategory> recipeCategoryMapper;

    public RecipeCategorySqlRepository(
            DataAccessObject<RecipeCategoryEntity> recipeCategoryDao,
            EntityMapper<RecipeCategoryEntity, RecipeCategory> recipeCategoryMapper) {
        this.recipeCategoryDao = recipeCategoryDao;
        this.recipeCategoryMapper = recipeCategoryMapper;
    }

    @Override
    public List<RecipeCategory> findAll() {
        return recipeCategoryDao
                .findAll()
                .stream()
                .map(recipeCategoryMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(RecipeCategory newEntity) {
        recipeCategoryDao.create(recipeCategoryMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(RecipeCategory entity) {
        var existingRecipeCategory = recipeCategoryDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("RecipeCategory not found, guid: " + entity.getGuid()));
        var updatedRecipeCategory = recipeCategoryMapper.mapExistingEntityToDatabase(entity, existingRecipeCategory.id());

        recipeCategoryDao.update(updatedRecipeCategory);
    }

    @Override
    public void deleteByGuid(String guid) {
        recipeCategoryDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        recipeCategoryDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return recipeCategoryDao.existsByGuid(guid);
    }

    @Override
    public Optional<RecipeCategory> findByGuid(String guid) {
        return recipeCategoryDao
                .findByGuid(guid)
                .map(recipeCategoryMapper::mapToBusiness);
    }
}
