package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;

/**
 * The interface represents a strategy for importing data into the system.
 * Implementations of this interface define the process of importing recipes and related entities
 * from a specified file using the provided services and batch importer.
 *
 * The interface offers basic static default methods for creating new entities.
 *
 * The {@link BatchImporter} interface is used for processing batches of data during the import process.
 * </p>
 *
 * @author Marek Eibel
 * @see BatchImporter
 */
public interface ImportStrategy {

    /**
     * Executes given ImportStrategy.
     *
     * @param recipeCrudService recipe CRUD service to use
     * @param ingredientCrudService ingredient CRUD service to use
     * @param unitCrudService unit CRUD service to use
     * @param recipeCategoryCrudService recipe category CRUD service to use
     * @param recipeIngredientAmountCrudService recipe ingredient amount CRUD service to use
     * @param batchImporter batch importer to use
     * @param filePath import data from the file
     */
    void executeImport(CrudService<Recipe> recipeCrudService, CrudService<Ingredient> ingredientCrudService,
                       CrudService<Unit> unitCrudService, CrudService<RecipeCategory> recipeCategoryCrudService,
                       CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService,
                       BatchImporter batchImporter, String filePath);

    /**
     * Creates a new recipe and adds it to the CRUD service.
     *
     * @param recipe the recipe to add
     * @param recipeCrudService the CRUD service for recipes
     */
    static void createRecipe(Recipe recipe, CrudService<Recipe> recipeCrudService) {
        recipeCrudService.create(recipe).intoException();
    }

    /**
     * Creates a new recipe category and adds it to the CRUD service.
     *
     * @param recipeCategory the recipe category to add
     * @param recipeCategoryCrudService the CRUD service for recipe categories
     */
    static void createRecipeCategory(RecipeCategory recipeCategory, CrudService<RecipeCategory> recipeCategoryCrudService) {
        recipeCategoryCrudService.create(recipeCategory).intoException();
    }

    /**
     * Creates a new ingredient and adds it to the CRUD service.
     *
     * @param ingredient the ingredient to add
     * @param ingredientCrudService the CRUD service for ingredients
     */
    static void createIngredient(Ingredient ingredient, CrudService<Ingredient> ingredientCrudService) {
        ingredientCrudService.create(ingredient).intoException();
    }

    /**
     * Creates a new unit and adds it to the CRUD service.
     *
     * @param unit the unit to add
     * @param unitCrudService the CRUD service for units
     */
    static void createUnit(Unit unit, CrudService<Unit> unitCrudService) {
        unitCrudService.create(unit).intoException();
    }

    /**
     * Creates a new recipe ingredient amount and adds it to the CRUD service.
     *
     * @param recipeIngredientAmount the recipe ingredient amount to add
     * @param recipeIngredientAmountCrudService the CRUD service for recipe ingredient amounts
     */
    static void createRecipeIngredientAmount(RecipeIngredientAmount recipeIngredientAmount, CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService) {
        recipeIngredientAmountCrudService.create(recipeIngredientAmount).intoException();
    }
}
