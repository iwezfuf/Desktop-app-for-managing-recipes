package cz.muni.fi.pv168.project.business.service.crud;


import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Ingredient} entity.
 */
public final class IngredientCrudService implements CrudService<Ingredient> {

    private final Repository<Ingredient> ingredientRepository;
    private final GuidProvider guidProvider;

    public IngredientCrudService(Repository<Ingredient> IngredientRepository,
                                 GuidProvider guidProvider) {
        this.ingredientRepository = IngredientRepository;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public ValidationResult create(Ingredient newEntity) {
        ingredientRepository.create(newEntity);

        return ValidationResult.success();
    }

    @Override
    public ValidationResult update(Ingredient entity) {
        ingredientRepository.update(entity);

        return ValidationResult.success();
    }

    @Override
    public void deleteByGuid(String guid) {
        ingredientRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        ingredientRepository.deleteAll();
    }
}
