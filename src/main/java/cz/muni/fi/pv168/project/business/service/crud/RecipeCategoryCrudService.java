package cz.muni.fi.pv168.project.business.service.crud;


import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.RecipeCategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import java.util.List;

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
        recipeCategoryRepository.create(newEntity);

        return ValidationResult.success();
    }

    @Override
    public ValidationResult update(RecipeCategory entity) {
        recipeCategoryRepository.update(entity);

        return ValidationResult.success();
    }

    @Override
    public void deleteByGuid(String guid) {
        recipeCategoryRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        recipeCategoryRepository.deleteAll();
    }
}
