package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeIngredientAmountCrudService;
import cz.muni.fi.pv168.project.business.service.crud.UnitCrudService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.json.JsonBatchExporter;
import cz.muni.fi.pv168.project.business.service.export.json.JsonBatchImporter;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeCategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.UnitValidator;
import cz.muni.fi.pv168.project.storage.sql.IngredientSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RecipeCategorySqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RecipeIngredientAmountSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RecipeSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.TransactionalImportService;
import cz.muni.fi.pv168.project.storage.sql.UnitSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.dao.IngredientDao;
import cz.muni.fi.pv168.project.storage.sql.dao.RecipeCategoryDao;
import cz.muni.fi.pv168.project.storage.sql.dao.RecipeDao;
import cz.muni.fi.pv168.project.storage.sql.dao.RecipeIngredientAmountDao;
import cz.muni.fi.pv168.project.storage.sql.dao.UnitDao;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionConnectionSupplier;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutorImpl;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionManagerImpl;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.IngredientMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RecipeCategoryMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RecipeIngredientAmountMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RecipeMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.UnitMapper;

import java.awt.*;
import java.util.List;

/**
 * Common dependency provider for both production and test environment.
 */
public class CommonDependencyProvider implements DependencyProvider {
    private final Repository<Recipe> recipes;
    private final Repository<Ingredient> ingredients;
    private final Repository<RecipeCategory> recipeCategories;
    private final Repository<Unit> units;
    private final Repository<RecipeIngredientAmount> recipeIngredientAmounts;
    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Unit> unitCrudService;
    private final CrudService<RecipeCategory> recipeCategoryCrudService;
    private final CrudService<RecipeIngredientAmount> recipeIngredientAmountCrudService;
    private final ImportService importService;
    private final ExportService exportService;
    private final RecipeValidator recipeValidator;
    private final IngredientValidator ingredientValidator;
    private final UnitValidator unitValidator;
    private final RecipeCategoryValidator recipeCategoryValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {

        recipeValidator = new RecipeValidator();
        ingredientValidator = new IngredientValidator();
        unitValidator = new UnitValidator();
        recipeCategoryValidator = new RecipeCategoryValidator();

        var recipeCategoryValidator = new RecipeCategoryValidator();
        var guidProvider = new UuidGuidProvider();

        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var recipeCategoryMapper = new RecipeCategoryMapper();
        var recipeCategoryDao = new RecipeCategoryDao(transactionConnectionSupplier);

        var unitDao = new UnitDao(transactionConnectionSupplier);
        var unitMapper = new UnitMapper(unitDao);

        var ingredientDao = new IngredientDao(transactionConnectionSupplier);
        var ingredientMapper = new IngredientMapper(unitDao, unitMapper);

        var recipeDao = new RecipeDao(transactionConnectionSupplier);
        var recipeIngredientAmountDao = new RecipeIngredientAmountDao(transactionConnectionSupplier);
        var recipeMapper = new RecipeMapper(recipeIngredientAmountDao, null,
                recipeCategoryDao, recipeCategoryMapper);

        var recipeIngredientAmountMapper = new RecipeIngredientAmountMapper(ingredientDao, ingredientMapper, recipeDao, recipeMapper);
        recipeMapper.setRecipeIngredientAmountMapper(recipeIngredientAmountMapper);

        this.recipeCategories = new RecipeCategorySqlRepository(
                recipeCategoryDao,
                recipeCategoryMapper
        );
        this.units = new UnitSqlRepository(
                unitDao,
                unitMapper
        );
        this.ingredients = new IngredientSqlRepository(
                ingredientDao,
                ingredientMapper
        );
        this.recipes = new RecipeSqlRepository(
                recipeDao,
                recipeMapper
        );
        this.recipeIngredientAmounts = new RecipeIngredientAmountSqlRepository(
                recipeIngredientAmountDao,
                recipeIngredientAmountMapper
        );

        recipeCategoryCrudService = new RecipeCategoryCrudService(recipeCategories, recipeCategoryValidator, guidProvider);
        unitCrudService = new UnitCrudService(units, unitValidator, guidProvider);
        ingredientCrudService = new IngredientCrudService(ingredients, ingredientValidator, guidProvider);

        recipeIngredientAmountCrudService = new RecipeIngredientAmountCrudService(recipeIngredientAmounts, guidProvider);
        recipeCrudService = new RecipeCrudService(recipes, recipeValidator, guidProvider, recipeIngredientAmountCrudService);

        initEntityDefaultValues();

        exportService = new GenericExportService(ingredientCrudService, recipeCrudService, unitCrudService, recipeCategoryCrudService, recipeIngredientAmountCrudService, List.of(new JsonBatchExporter()));
        importService = new TransactionalImportService(ingredientCrudService, recipeCrudService, unitCrudService, recipeCategoryCrudService, recipeIngredientAmountCrudService, List.of(new JsonBatchImporter()), transactionExecutor);
    }

    private void initEntityDefaultValues() {
        // Add no category to the list of categories, only if there are no categories in the database
//        if (recipeCategoryCrudService.findAll().isEmpty()) {
//            recipeCategoryCrudService.create(new RecipeCategory("No category", Color.LIGHT_GRAY));
//        }
        // Add base units to the list of units, only if there are no units in the database
        if (unitCrudService.findAll().isEmpty()) {
//            unitCrudService.create(new Unit("gram", null , 1, "g"));
//            unitCrudService.create(new Unit("liter", null , 1, "l"));
//            unitCrudService.create(new Unit("piece", null , 1, "pcs"));
        }
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public Repository<Recipe> getRecipeRepository() {
        return recipes;
    }

    @Override
    public Repository<RecipeCategory> getRecipeCategoryRepository() {
        return recipeCategories;
    }

    @Override
    public Repository<Ingredient> getRecipeIngredientRepository() {
        return ingredients;
    }

    @Override
    public Repository<RecipeIngredientAmount> getRecipeIngredientAmountRepository() {
        return recipeIngredientAmounts;
    }

    @Override
    public Repository<Unit> getUnitRepository() {
        return units;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }

    @Override
    public ImportService getImportService() {
        return importService;
    }

    @Override
    public ExportService getExportService() {
        return exportService;
    }

    @Override
    public CrudService<Ingredient> getIngredientCrudService() {
        return ingredientCrudService;
    }

    @Override
    public CrudService<Recipe> getRecipeCrudService() {
        return recipeCrudService;
    }

    @Override
    public CrudService<Unit> getUnitCrudService() {
        return unitCrudService;
    }

    @Override
    public CrudService<RecipeCategory> getRecipeCategoryCrudService() {
        return recipeCategoryCrudService;
    }

    @Override
    public RecipeValidator getRecipeValidator() {
        return recipeValidator;
    }

    @Override
    public IngredientValidator getIngredientValidator() {
        return ingredientValidator;
    }

    @Override
    public UnitValidator getUnitValidator() {
        return unitValidator;
    }

    @Override
    public RecipeCategoryValidator getRecipeCategoryValidator() {
        return recipeCategoryValidator;
    }

    @Override
    public CrudService<RecipeIngredientAmount> getRecipeIngredientAmountCrudService() {
        return recipeIngredientAmountCrudService;
    }

}
