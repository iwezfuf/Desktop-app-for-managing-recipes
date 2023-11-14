package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.DepartmentCrudService;
import cz.muni.fi.pv168.project.business.service.crud.EmployeeCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCategoryCrudService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericImportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.DepartmentValidator;
import cz.muni.fi.pv168.project.business.service.validation.EmployeeValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeCategoryValidator;
import cz.muni.fi.pv168.project.export.csv.BatchCsvExporter;
import cz.muni.fi.pv168.project.export.csv.BatchCsvImporter;
import cz.muni.fi.pv168.project.storage.sql.DepartmentSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.EmployeeSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RecipeCategorySqlRepository;
import cz.muni.fi.pv168.project.storage.sql.dao.DepartmentDao;
import cz.muni.fi.pv168.project.storage.sql.dao.EmployeeDao;
import cz.muni.fi.pv168.project.storage.sql.dao.RecipeCategoryDao;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionConnectionSupplier;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutorImpl;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionManagerImpl;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.DepartmentMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EmployeeMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RecipeCategoryMapper;

import java.util.List;

/**
 * Common dependency provider for both production and test environment.
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final Repository<Department> departments;
    private final Repository<Employee> employees;
    private final Repository<Recipe> recipes;
    private final Repository<Ingredient> ingredients;
    private final Repository<RecipeCategory> recipeCategories;
    private final Repository<Unit> units;
    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final CrudService<Employee> employeeCrudService;
    private final CrudService<Department> departmentCrudService;
    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Unit> unitCrudService;
    private final CrudService<RecipeCategory> recipeCategoryCrudService;
    private final ImportService importService;
    private final ExportService exportService;
    private final EmployeeValidator employeeValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        employeeValidator = new EmployeeValidator();
        var departmentValidator = new DepartmentValidator();
        var recipeCategoryValidator = new RecipeCategoryValidator();
        var guidProvider = new UuidGuidProvider();

        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var recipeCategoryMapper = new RecipeCategoryMapper();
        var recipeCategoryDao = new RecipeCategoryDao(transactionConnectionSupplier);

        var departmentMapper = new DepartmentMapper();
        var departmentDao = new DepartmentDao(transactionConnectionSupplier);

        var employeeMapper = new EmployeeMapper(departmentDao, departmentMapper);

        this.departments = new DepartmentSqlRepository(
                departmentDao,
                departmentMapper
        );
        this.employees = new EmployeeSqlRepository(
                new EmployeeDao(transactionConnectionSupplier),
                employeeMapper
        );
        this.recipeCategories = new RecipeCategorySqlRepository(
                recipeCategoryDao,
                recipeCategoryMapper
        );
        this.units = new UnitSqlRepository(
                new UnitDao(transactionConnectionSupplier),
                new UnitMapper()
        );
        this.ingredients = new IngredientSqlRepository(
                new IngredientDao(transactionConnectionSupplier),
                new IngredientMapper(units)
        );
        this.recipes = new RecipeSqlRepository(
                new RecipeDao(transactionConnectionSupplier),
                new RecipeMapper(ingredients, recipeCategories)
        );
        recipeCategoryCrudService = new RecipeCategoryCrudService(recipeCategories, recipeCategoryValidator, guidProvider);
        departmentCrudService = new DepartmentCrudService(departments, departmentValidator, guidProvider);
        employeeCrudService = new EmployeeCrudService(employees, employeeValidator, guidProvider);
        exportService = new GenericExportService(employeeCrudService, departmentCrudService,
                List.of(new BatchCsvExporter()));
        importService = new GenericImportService(employeeCrudService, departmentCrudService,
                ingredientCrudService, recipeCrudService, unitCrudService, recipeCategoryCrudService,
                List.of(new BatchCsvImporter()));
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public Repository<Department> getDepartmentRepository() {
        return departments;
    }

    @Override
    public Repository<Employee> getEmployeeRepository() {
        return employees;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }

    @Override
    public CrudService<Employee> getEmployeeCrudService() {
        return employeeCrudService;
    }

    @Override
    public CrudService<Department> getDepartmentCrudService() {
        return departmentCrudService;
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
    public EmployeeValidator getEmployeeValidator() {
        return employeeValidator;
    }
}
