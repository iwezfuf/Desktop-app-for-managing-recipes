package cz.muni.fi.pv168.project.business.service.crud;


import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.RecipeCategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

/**
 * Crud operations for the {@link RecipeCategory} entity.
 */
public final class RecipeCategoryCrudService implements CrudService<RecipeCategory> {

    private final Repository<RecipeCategory> recipeCategoryRepository;
    private final RecipeCategoryValidator recipeCategoryValidator;
    private final GuidProvider guidProvider;

    public RecipeCategoryCrudService(Repository<RecipeCategory> RecipeCategoryRepository,
                                     RecipeCategoryValidator recipeCategoryValidator, GuidProvider guidProvider) {
        this.recipeCategoryRepository = RecipeCategoryRepository;
        this.recipeCategoryValidator = recipeCategoryValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<RecipeCategory> findAll() {
        return recipeCategoryRepository.findAll();
    }

    @Override
    public ValidationResult create(RecipeCategory newEntity) {
        var validationResult = recipeCategoryValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (recipeCategoryRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("RecipeCategory with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            recipeCategoryRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(RecipeCategory entity) {
        recipeCategoryRepository.update(entity);

        return ValidationResult.success();
    }

    @Override
    public boolean deleteByGuid(String guid) {
        String recipeCategoryName = recipeCategoryRepository.findByGuid(guid).get().getName();
        if (Objects.equals(recipeCategoryName, "No category")) {
            JOptionPane.showMessageDialog(null, "Cannot delete base category",
                    "Deletion error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        recipeCategoryRepository.deleteByGuid(guid);
        return true;
    }

    @Override
    public void deleteAll() {
        recipeCategoryRepository.deleteAll();
    }
}
