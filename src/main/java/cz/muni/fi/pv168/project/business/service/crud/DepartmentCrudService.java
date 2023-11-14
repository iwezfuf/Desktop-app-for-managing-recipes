package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Department} entity.
 */
public class DepartmentCrudService implements CrudService<Department> {

    private final Repository<Department> departmentRepository;
    private final Validator<Department> departmentValidator;
    private final GuidProvider guidProvider;

    public DepartmentCrudService(Repository<Department> departmentRepository,
                                 Validator<Department> departmentValidator,
                                 GuidProvider guidProvider) {
        this.departmentRepository = departmentRepository;
        this.departmentValidator = departmentValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public ValidationResult create(Department newEntity) {
        var validationResult = departmentValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (departmentRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Department with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            departmentRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Department entity) {
        var validationResult = departmentValidator.validate(entity);
        if (validationResult.isValid()) {
            departmentRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        departmentRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        departmentRepository.deleteAll();
    }
}
