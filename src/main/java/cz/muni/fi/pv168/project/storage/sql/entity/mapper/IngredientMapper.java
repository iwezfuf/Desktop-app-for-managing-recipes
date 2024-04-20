package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;

/**
 * Mapper from the {@link IngredientEntity} to {@link Ingredient}.
 */
public class IngredientMapper implements EntityMapper<IngredientEntity, Ingredient> {

    private final DataAccessObject<UnitEntity> unitDao;
    private final EntityMapper<UnitEntity, Unit> unitMapper;

    public IngredientMapper(
            DataAccessObject<UnitEntity> unitDao, EntityMapper<UnitEntity, Unit> unitMapper) {
        this.unitDao = unitDao;
        this.unitMapper = unitMapper;
    }

    @Override
    public Ingredient mapToBusiness(IngredientEntity entity) {
        var unit = unitDao
                .findById(entity.unitId())
                .map(unitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Unit not found, id: " +
                        entity.unitId()));

        return new Ingredient(
                entity.guid(),
                entity.name(),
                entity.nutritionalValue(),
                unit
        );
    }

    @Override
    public IngredientEntity mapNewEntityToDatabase(Ingredient entity) {
        var unit = unitDao
                .findByGuid(entity.getUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getUnit().getGuid()));

        return new IngredientEntity(
                entity.getGuid(),
                entity.getName(),
                entity.getNutritionalValue(),
                unit.id()
        );
    }

    @Override
    public IngredientEntity mapExistingEntityToDatabase(Ingredient entity, Long dbId) {
        var unit = unitDao
                .findByGuid(entity.getUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getUnit().getGuid()));

        return new IngredientEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getNutritionalValue(),
                unit.id()
        );
    }
}
