package cz.muni.fi.pv168.project.business.service.crud;


import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Unit} entity.
 */
public final class UnitCrudService implements CrudService<Unit> {

    private final Repository<Unit> unitRepository;
    private final Validator<Unit> unitValidator;
    private final GuidProvider guidProvider;

    public UnitCrudService(Repository<Unit> UnitRepository, Validator<Unit> UnitValidator,
                                 GuidProvider guidProvider) {
        this.unitRepository = UnitRepository;
        this.unitValidator = UnitValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public ValidationResult create(Unit newEntity) {
        var validationResult = unitValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (unitRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Unit with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            unitRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Unit entity) {
        unitRepository.update(entity);

        return ValidationResult.success();
    }

    @Override
    public void deleteByGuid(String guid) {
        unitRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        unitRepository.deleteAll();
    }
}
