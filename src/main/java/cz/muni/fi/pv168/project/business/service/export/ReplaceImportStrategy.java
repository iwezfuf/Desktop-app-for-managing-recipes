package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import java.util.Collection;

/**
 * @author Marek Eibel
 */
public class ReplaceImportStrategy implements ImportStrategy {
    @Override
    public void executeImport(CrudService<Recipe> recipeCrudService, CrudService<Ingredient> ingredientCrudService,
                              CrudService<Unit> unitCrudService, CrudService<RecipeCategory> recipeCategoryCrudService,
                              CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService,
                              BatchImporter batchImporter, String filePath) {

        recipeIngredientAmountCrudService.deleteAll();
        recipeCrudService.deleteAll();
        recipeCategoryCrudService.deleteAll();
        ingredientCrudService.deleteAll();
        unitCrudService.deleteAll();

        var batch = batchImporter.importBatch(filePath);

        batch.recipeCategories().forEach(recipeCategory -> ImportStrategy.createRecipeCategory(recipeCategory, recipeCategoryCrudService));
        batch.units().forEach(unit -> ImportStrategy.createUnit(unit, unitCrudService));
        batch.ingredients().forEach(ingredient -> ImportStrategy.createIngredient(ingredient, ingredientCrudService));
        batch.recipeIngredientAmounts().forEach(recipeIngredientAmount -> ImportStrategy.createRecipeIngredientAmount(recipeIngredientAmount, recipeIngredientAmountCrudService));
        Collection<Recipe> recipes = batch.recipes();
        for (Recipe recipe : recipes) {
            ImportStrategy.createRecipe(recipe, recipeCrudService);
            for (RecipeIngredientAmount recipeIngredientAmount : recipe.getIngredients()) {
                recipeIngredientAmount.setRecipe(recipe);
                recipeIngredientAmountCrudService.update(recipeIngredientAmount);
            }
        }
    }
}
