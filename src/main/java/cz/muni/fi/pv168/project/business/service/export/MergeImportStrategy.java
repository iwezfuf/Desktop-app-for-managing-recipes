package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

        batch.recipeCategories().forEach(recipeCategory -> ImportStrategy.createRecipeCategory(recipeCategory, recipeCategoryCrudService));
        batch.units().forEach(unit -> createUnit(unit, unitCrudService));
        batch.ingredients().forEach(ingredient -> createIngredient(ingredient, ingredientCrudService));
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

    private static void createIngredient(Ingredient ingredient, CrudService<Ingredient> ingredientCrudService) { // TODO: create dialog window with all duplicates where the user has a chance which version should be used
        List<Ingredient> ingredientList = ingredientCrudService.findAll();
        for (Ingredient ingredientInList : ingredientList) {
            if (!Objects.equals(ingredientInList.getName(), ingredient.getName())) {
                new JOptionPane().showMessageDialog(null, "Unable to merge duplicate ingredient: " + ingredient, "Error", JOptionPane.ERROR_MESSAGE);
                break;
            } else {
                ingredientCrudService.create(ingredient).intoException();
            }
        }
    }

    private static void createUnit(Unit unit, CrudService<Unit> unitCrudService) {
        List<Unit> unitList = unitCrudService.findAll();
        for (Unit unitInList : unitList) {
            if (!Objects.equals(unitInList.getName(), unit.getName())) {
                new JOptionPane().showMessageDialog(null, "Unable to merge duplicate unit: " + unit, "Error", JOptionPane.ERROR_MESSAGE);
                break;
            } else {
                unitCrudService.create(unit).intoException();
            }
        }
    }
}
