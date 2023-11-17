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
        Unit conversionUnit = null;
        if (entity.conversionUnitId() != 0) {
            conversionUnit = unitDao
                    .findById(entity.conversionUnitId())
                    .map(this::mapToBusiness)
                    .orElseThrow(() -> new DataStorageException("Unit not found, id: " +
                            entity.conversionUnitId()));
        }

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
        long conversionUnitEntityId = 0;
        if (entity.getConversionUnit() != null) {
            conversionUnitEntityId = (unitDao
                .findByGuid(entity.getConversionUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getConversionUnit().getGuid()))).id();
        }

        return new UnitEntity(
            entity.getGuid(),
            entity.getName(),
            entity.getAbbreviation(),
            entity.getConversionRatio(),
            conversionUnitEntityId
        );
    }

    @Override
    public UnitEntity mapExistingEntityToDatabase(Unit entity, Long dbId) {
        long conversionUnitEntityId = 0;
        if (entity.getConversionUnit() != null) {
            conversionUnitEntityId = (unitDao
                .findByGuid(entity.getConversionUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getConversionUnit().getGuid()))).id();
        }

        return new UnitEntity(
            dbId,
            entity.getGuid(),
            entity.getName(),
            entity.getAbbreviation(),
            entity.getConversionRatio(),
            conversionUnitEntityId
        );
    }
}
