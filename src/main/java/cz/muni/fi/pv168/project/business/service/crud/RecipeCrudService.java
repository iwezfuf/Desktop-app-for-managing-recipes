package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.dao.InvalidDataDeletionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Crud operations for the {@link Recipe} entity.
 */
public class RecipeCrudService implements CrudService<Recipe> {

    private final Repository<Recipe> recipeRepository;
    private final CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService;
    private final GuidProvider guidProvider;
    private final Validator<Recipe> recipeValidator;

    public RecipeCrudService(Repository<Recipe> recipeRepository, Validator<Recipe> recipeValidator,
                             GuidProvider guidProvider, CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientAmountCrudService = recipeIngredientAmountCrudService;
        this.guidProvider = guidProvider;
        this.recipeValidator = recipeValidator;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public int getTotalNumberOfRecipes() {
        return recipeRepository.findAll().size();
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
        newEntity.setIngredients(storedRecipeIngredientAmounts);

        if (validationResult.isValid()) {
            recipeRepository.create(newEntity);
        }
        updateIngredientAmounts(newEntity);

        return validationResult;
    }

    @Override
    public ValidationResult update(Recipe entity) {
        var validationResult = recipeValidator.validate(entity);
        if (validationResult.isValid()) {
            recipeRepository.update(entity);
            updateIngredientAmounts(entity);
        }
        return validationResult;
    }

    public void updateIngredientAmounts(Recipe entity) {
        for (RecipeIngredientAmount recipeIngredientAmount : entity.getIngredients()) {
            if (recipeIngredientAmount.getGuid() != null) {
                recipeIngredientAmountCrudService.update(recipeIngredientAmount);
            } else {
                recipeIngredientAmountCrudService.create(recipeIngredientAmount);
            }
        }
    }

    @Override
    public boolean deleteByGuid(String guid) {
        for (RecipeIngredientAmount recipeIngredientAmount : recipeRepository.findByGuid(guid).get().getIngredients()) {
            recipeIngredientAmountCrudService.deleteByGuid(recipeIngredientAmount.getGuid());
        }
        try {
            recipeRepository.deleteByGuid(guid);
        } catch (InvalidDataDeletionException e) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteAll() {
        for (Recipe recipe : recipeRepository.findAll()) {
            deleteByGuid(recipe.getGuid());
        }
    }

    /**
     * Retrieves the total number of recipes, where the ingredient is used.
     *
     * @param ingredient usage of this ingredient
     * @return total number of recipes, where the ingredient is used.
     */
    public int getNumberOfRecipesWithIngredient(Ingredient ingredient) {

        int count = 0;

        for (Recipe recipe : findAll()) {
            for (RecipeIngredientAmount ingredientAmount : recipe.getIngredients()) {
                if (ingredientAmount.getIngredient().equals(ingredient)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
