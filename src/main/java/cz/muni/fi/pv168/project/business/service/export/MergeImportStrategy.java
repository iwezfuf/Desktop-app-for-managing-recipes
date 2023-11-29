package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.EntityAlreadyExistsException;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;

import java.util.Collection;

/**
 * Represents merge import strategy. Duplicate of units & ingredients will not be stored.
 *
 * @author Marek Eibel
 */
public class MergeImportStrategy implements ImportStrategy {

    @Override
    public void executeImport(CrudService<Recipe> recipeCrudService, CrudService<Ingredient> ingredientCrudService,
                              CrudService<Unit> unitCrudService, CrudService<RecipeCategory> recipeCategoryCrudService,
                              CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService,
                              BatchImporter batchImporter, String filePath) {

        var batch = batchImporter.importBatch(filePath);

        // Only create new entity if their guid is not already in the database
        batch.recipeCategories().forEach(recipeCategory -> {
            try {
                recipeCategoryCrudService.create(recipeCategory);
            } catch (EntityAlreadyExistsException e) {
                // Ignore
            }
        });
        batch.units().forEach(unit -> {
            try {
                unitCrudService.create(unit);
            } catch (EntityAlreadyExistsException e) {
                // Ignore
            }
        });
        batch.ingredients().forEach(ingredient -> {
            try {
                ingredientCrudService.create(ingredient);
            } catch (EntityAlreadyExistsException e) {
                // Ignore
            }
        });
        batch.recipeIngredientAmounts().forEach(recipeIngredientAmount -> {
            try {
                recipeIngredientAmountCrudService.create(recipeIngredientAmount);
            } catch (EntityAlreadyExistsException e) {
                // Ignore
            }
        });
        Collection<Recipe> recipes = batch.recipes();
        for (Recipe recipe : recipes) {
            try {
                recipeCrudService.create(recipe);
            } catch (EntityAlreadyExistsException e) {
                // Ignore
            }
            for (RecipeIngredientAmount recipeIngredientAmount : recipe.getIngredients()) {
                recipeIngredientAmount.setRecipe(recipe);
                recipeIngredientAmountCrudService.update(recipeIngredientAmount);
            }
        }
    }
}
