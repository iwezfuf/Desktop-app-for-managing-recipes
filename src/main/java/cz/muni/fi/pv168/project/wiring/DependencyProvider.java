package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;

/**
 * Interface for instance wiring
 */
public interface DependencyProvider {

    DatabaseManager getDatabaseManager();
    Repository<Recipe> getRecipeRepository();
    Repository<RecipeCategory> getRecipeCategoryRepository();
    Repository<Ingredient> getRecipeIngredientRepository();
    Repository<RecipeIngredientAmount> getRecipeIngredientAmountRepository();
    Repository<Unit> getUnitRepository();
    TransactionExecutor getTransactionExecutor();
    CrudService<Recipe> getRecipeCrudService();
    CrudService<RecipeCategory> getRecipeCategoryCrudService();
    CrudService<Ingredient> getIngredientCrudService();
    CrudService<RecipeIngredientAmount> getRecipeIngredientAmountCrudService();
    CrudService<Unit> getUnitCrudService();
    ImportService getImportService();
    ExportService getExportService();
    Validator<Recipe> getRecipeValidator();
    Validator<Ingredient> getIngredientValidator();
    Validator<Unit> getUnitValidator();
    Validator<RecipeCategory> getRecipeCategoryValidator();
}

