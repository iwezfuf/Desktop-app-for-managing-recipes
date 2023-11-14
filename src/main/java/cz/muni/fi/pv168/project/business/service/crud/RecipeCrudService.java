package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Recipe} entity.
 */
public class RecipeCrudService implements CrudService<Recipe> {

    private final Repository<Recipe> recipeRepository;
    private final GuidProvider guidProvider;
    private final Validator<Recipe> recipeValidator;

    public RecipeCrudService(Repository<Recipe> RecipeRepository, Validator<Recipe> recipeValidator,
                             GuidProvider guidProvider) {
        this.recipeRepository = RecipeRepository;
        this.guidProvider = guidProvider;
        this.recipeValidator = recipeValidator;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public ValidationResult create(Recipe newEntity) {

        var validationResult = recipeValidator.validate(newEntity);
        if (validationResult.isValid()) {
            System.out.println("Adding recipe: " + newEntity.getName() + " into crud");
            recipeRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Recipe entity) {
        var validationResult = recipeValidator.validate(entity);
        if (validationResult.isValid()) {
            recipeRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        recipeRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        recipeRepository.deleteAll();
    }
}
