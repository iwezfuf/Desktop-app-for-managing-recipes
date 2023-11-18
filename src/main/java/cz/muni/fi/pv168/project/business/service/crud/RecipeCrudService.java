package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Crud operations for the {@link Recipe} entity.
 */
public class RecipeCrudService implements CrudService<Recipe> {

    private final Repository<Recipe> recipeRepository;
    private final CrudService<RecipeIngredientAmount> ingredientAmountCrudService;
    private final GuidProvider guidProvider;
    private final Validator<Recipe> recipeValidator;

    public RecipeCrudService(Repository<Recipe> recipeRepository, Validator<Recipe> recipeValidator,
                             GuidProvider guidProvider, CrudService<RecipeIngredientAmount> recipeIngredientAmountRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientAmountCrudService = recipeIngredientAmountRepository;
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

        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (recipeRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Recipe with given guid already exists: " + newEntity.getGuid());
        }

        ArrayList<RecipeIngredientAmount> storedRecipeIngredientAmounts = newEntity.getIngredients();
        newEntity.setIngredients(new ArrayList<>());

        if (validationResult.isValid()) {
            recipeRepository.create(newEntity);
        }

        for (RecipeIngredientAmount recipeIngredientAmount:storedRecipeIngredientAmounts) {
            ingredientAmountCrudService.create(recipeIngredientAmount);
        }

        newEntity.setIngredients(storedRecipeIngredientAmounts);
        update(newEntity);

//        System.out.println("Adding recipe: <" + newEntity + "> into crud");
//
//        var x = recipeRepository.findAll();
//        for (int i = 0; i < x.size(); ++i) {
//            System.out.println(x.get(i));
//        }

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
