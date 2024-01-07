package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.ImportStrategy;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;

import java.util.Collection;

public class TransactionalImportService implements ImportService {

    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Unit> unitCrudService;
    private final CrudService<RecipeCategory> recipeCategoryCrudService;
    private final CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService;
    private final FormatMapping<BatchImporter> importers;
    private final TransactionExecutor transactionExecutor;

    public TransactionalImportService(
            CrudService<Ingredient> IngredientCrudService,
            CrudService<Recipe> RecipeCrudService,
            CrudService<Unit> UnitCrudService,
            CrudService<RecipeCategory> recipeCategoryCrudService,
            CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService,
            Collection<BatchImporter> importers,
            TransactionExecutor transactionExecutor
    ) {
        this.ingredientCrudService = IngredientCrudService;
        this.recipeCrudService = RecipeCrudService;
        this.unitCrudService = UnitCrudService;
        this.recipeCategoryCrudService = recipeCategoryCrudService;
        this.recipeIngredientAmountCrudService = recipeIngredientAmountCrudService;
        this.importers = new FormatMapping<>(importers);
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void importData(String filePath, ImportStrategy importStrategy) {
        if (importStrategy == null) {
            throw new NullPointerException("Import strategy has not been set.");
        }

        transactionExecutor.executeInTransaction(() -> importStrategy.executeImport(recipeCrudService, ingredientCrudService, unitCrudService, recipeCategoryCrudService, recipeIngredientAmountCrudService, getImporter(filePath), filePath));
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }

        return importer;
    }

    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }
}
