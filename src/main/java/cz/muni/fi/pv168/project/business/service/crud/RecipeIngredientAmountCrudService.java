package cz.muni.fi.pv168.project.business.service.crud;


import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link RecipeIngredientAmount} entity.
 */
public final class RecipeIngredientAmountCrudService implements CrudService<RecipeIngredientAmount> {

    private final Repository<RecipeIngredientAmount> recipeRecipeIngredientAmountAmountRepository;
    private final GuidProvider guidProvider;

    public RecipeIngredientAmountCrudService(Repository<RecipeIngredientAmount> RecipeIngredientAmountRepository,
                                 GuidProvider guidProvider) {
        this.recipeRecipeIngredientAmountAmountRepository = RecipeIngredientAmountRepository;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<RecipeIngredientAmount> findAll() {
        return recipeRecipeIngredientAmountAmountRepository.findAll();
    }

    @Override
    public ValidationResult create(RecipeIngredientAmount newEntity) {

        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (recipeRecipeIngredientAmountAmountRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("RecipeIngredientAmount with given guid already exists: " + newEntity.getGuid());
        }

        recipeRecipeIngredientAmountAmountRepository.create(newEntity);

        return ValidationResult.success();
    }

    @Override
    public ValidationResult update(RecipeIngredientAmount entity) {
        recipeRecipeIngredientAmountAmountRepository.update(entity);

        return ValidationResult.success();
    }

    @Override
    public boolean deleteByGuid(String guid) {
        recipeRecipeIngredientAmountAmountRepository.deleteByGuid(guid);
        return true;
    }

    @Override
    public void deleteAll() {
        recipeRecipeIngredientAmountAmountRepository.deleteAll();
    }
}
