package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.DepartmentEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.EmployeeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;

/**
 * Mapper from the {@link UnitEntity} to {@link Unit}.
 */
public class UnitMapper implements EntityMapper<UnitEntity, Unit> {

    private final DataAccessObject<UnitEntity> unitDao;

    public UnitMapper(
            DataAccessObject<UnitEntity> unitDao) {
        this.unitDao = unitDao;
    }

    @Override
    public Unit mapToBusiness(UnitEntity entity) {
        var conversionUnit = unitDao
                .findById(entity.conversionUnitId())
                .map(this::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Unit not found, id: " +
                        entity.conversionUnitId()));

        return new Unit(
                entity.guid(),
                entity.name(),
                conversionUnit,
                entity.conversionRatio(),
                entity.abbreviation()
        );
    }

    @Override
    public UnitEntity mapNewEntityToDatabase(Unit entity) {
        var unitEntity = unitDao
                .findByGuid(entity.getConversionUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getConversionUnit().getGuid()));

        return new UnitEntity(
            entity.getGuid(),
            entity.getName(),
            entity.getAbbreviation(),
            entity.getConversionRatio(),
            unitEntity.id()
        );
    }

    @Override
    public UnitEntity mapExistingEntityToDatabase(Unit entity, Long dbId) {
        var unitEntity = unitDao
                .findByGuid(entity.getConversionUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getConversionUnit().getGuid()));

        return new UnitEntity(
            dbId,
            entity.getGuid(),
            entity.getName(),
            entity.getAbbreviation(),
            entity.getConversionRatio(),
            unitEntity.id()
        );
    }
}
