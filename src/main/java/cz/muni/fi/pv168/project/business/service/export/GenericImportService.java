package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;

import java.util.Collection;

/**
 * Generic synchronous implementation of the {@link ImportService}.
 */
public class GenericImportService implements ImportService {

    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Unit> unitCrudService;
    private final CrudService<RecipeCategory> recipeCategoryCrudService;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            CrudService<Ingredient> IngredientCrudService,
            CrudService<Recipe> RecipeCrudService,
            CrudService<Unit> UnitCrudService,
            CrudService<RecipeCategory> recipeCategoryCrudService,
            Collection<BatchImporter> importers
    ) {
        this.ingredientCrudService = IngredientCrudService;
        this.recipeCrudService = RecipeCrudService;
        this.unitCrudService = UnitCrudService;
        this.recipeCategoryCrudService = recipeCategoryCrudService;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath, ImportType importType) {
        if (importType == ImportType.REPLACE) {
            Recipe.getListOfRecipes().clear();
            Ingredient.getListOfIngredients().clear();
            RecipeCategory.getListOfCategories().clear();
            Unit.getListOfUnits().clear();
        }

        ingredientCrudService.deleteAll();
        recipeCrudService.deleteAll();
        recipeCategoryCrudService.deleteAll();
        unitCrudService.deleteAll();

        var batch = getImporter(filePath).importBatch(filePath);

        batch.recipes().forEach(this::createRecipe);
        batch.ingredients().forEach(this::createIngredient);
        batch.units().forEach(this::createUnit);
        batch.recipeCategories().forEach(this::createRecipeCategory);
    }

    private void createRecipe(Recipe recipe) {
        //Recipe.getListOfRecipes().add(Recipe);
        recipeCrudService.create(recipe)
                .intoException();
    }

    private void createIngredient(Ingredient ingredient) {
        ingredientCrudService.create(ingredient)
                .intoException();
    }

    private void createUnit(Unit unit) {
        unitCrudService.create(unit)
                .intoException();
    }

    private void createRecipeCategory(RecipeCategory recipeCategory) {
        recipeCategoryCrudService.create(recipeCategory)
                .intoException();
    }

    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }

        return importer;
    }
}
