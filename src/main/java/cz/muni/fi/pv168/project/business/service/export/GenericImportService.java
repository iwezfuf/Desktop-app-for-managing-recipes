package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
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
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            CrudService<Ingredient> IngredientCrudService,
            CrudService<Recipe> RecipeCrudService,
            Collection<BatchImporter> importers
    ) {
        this.ingredientCrudService = IngredientCrudService;
        this.recipeCrudService = RecipeCrudService;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath) {
        ingredientCrudService.deleteAll();
        recipeCrudService.deleteAll();

        var batch = getImporter(filePath).importBatch(filePath);

        batch.recipes().forEach(this::createRecipe);
        batch.ingredients().forEach(this::createIngredient);
    }

    private void createRecipe(Recipe Recipe) {
        Recipe.getListOfRecipes().add(Recipe);
        recipeCrudService.create(Recipe)
                .intoException();
    }

    private void createIngredient(Ingredient Ingredient) {
        ingredientCrudService.create(Ingredient)
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
