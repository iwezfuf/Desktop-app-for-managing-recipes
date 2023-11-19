package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Gender;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.ui.action.AboutUsAction;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.ExportAction;
import cz.muni.fi.pv168.project.ui.action.ImportAction;
import cz.muni.fi.pv168.project.ui.action.AddAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.action.NuclearQuitAction;
import cz.muni.fi.pv168.project.ui.action.QuitAction;
import cz.muni.fi.pv168.project.ui.dialog.EmployeeDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeCategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;
import cz.muni.fi.pv168.project.ui.filters.EmployeeTableFilter;
import cz.muni.fi.pv168.project.ui.filters.components.FilterComboboxBuilder;
import cz.muni.fi.pv168.project.ui.filters.components.FilterListModelBuilder;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterDepartmentValues;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterGenderValues;
import cz.muni.fi.pv168.project.ui.model.DepartmentListModel;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.model.Column;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.panels.EmployeeTablePanel;
import cz.muni.fi.pv168.project.ui.panels.EntityTablePanel;
import cz.muni.fi.pv168.project.ui.panels.IngredientTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeCategoryTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;
import cz.muni.fi.pv168.project.ui.panels.UnitTablePanel;
import cz.muni.fi.pv168.project.ui.renderers.DepartmentRenderer;
import cz.muni.fi.pv168.project.ui.renderers.GenderRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterDepartmentValuesRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterGenderValuesRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Either;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainWindow {

    private final JFrame frame;
    private final Action quitAction = new QuitAction();
    private final Action nuclearQuit;
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final Action exportAction;
    private final Action importAction;
    private final AboutUsAction aboutUsAction;
    private final EntityTableModel<Employee> employeeTableModel;
    private final EntityTableModel<Recipe> recipeTableModel;
    private final EntityTableModel<Ingredient> ingredientTableModel;
    private final EntityTableModel<Unit> unitTableModel;
    private final EntityTableModel<RecipeCategory> recipeCategoryTableModel;
    private final DepartmentListModel departmentListModel;
    private final EntityTableModelProvider entityTableModelProvider;

    public MainWindow(DependencyProvider dependencyProvider) {

        frame = createFrame();

        employeeTableModel = createEmployeeTableModel(dependencyProvider);
        recipeTableModel = createRecipeTableModel(dependencyProvider);
        ingredientTableModel = createIngredientTableModel(dependencyProvider);
        unitTableModel = createUnitTableModel(dependencyProvider);
        recipeCategoryTableModel = createRecipeCategoryTableModel(dependencyProvider);
        // TODO delete departmentListModel later
        departmentListModel = new DepartmentListModel(dependencyProvider.getDepartmentCrudService());

        entityTableModelProvider = new EntityTableModelProvider(
                employeeTableModel,
                recipeTableModel,
                ingredientTableModel,
                unitTableModel,
                recipeCategoryTableModel,
                dependencyProvider.getRecipeIngredientAmountCrudService(),
                departmentListModel
        );

        // Only run this once to fill the database with test data
        if (recipeTableModel.getRowCount() == 0) {
            TestDataGenerator testDataGenerator = new TestDataGenerator();
            testDataGenerator.fillTables(entityTableModelProvider);
        }

        Validator<Employee> employeeValidator = dependencyProvider.getEmployeeValidator();
        Validator<Recipe> recipeValidator = dependencyProvider.getRecipeValidator();
        Validator<Ingredient> ingredientValidator = dependencyProvider.getIngredientValidator();
        Validator<Unit> unitValidator = dependencyProvider.getUnitValidator();
        Validator<RecipeCategory> recipeCategoryValidator = dependencyProvider.getRecipeCategoryValidator();

        var employeeTablePanel = new EmployeeTablePanel(employeeTableModel, employeeValidator, EmployeeDialog.class, this::changeActionsState);
        var recipeTablePanel = new RecipeTablePanel(recipeTableModel, recipeValidator, RecipeDialog.class, this::changeActionsState, frame.getHeight());
        var ingredientTablePanel = new IngredientTablePanel(ingredientTableModel, ingredientValidator, IngredientDialog.class, this::changeActionsState, frame.getHeight());
        var unitTablePanel = new UnitTablePanel(unitTableModel, unitValidator, UnitDialog.class, this::changeActionsState);
        var recipeCategoryTablePanel = new RecipeCategoryTablePanel(recipeCategoryTableModel, recipeCategoryValidator, RecipeCategoryDialog.class, this::changeActionsState);

        nuclearQuit = new NuclearQuitAction(dependencyProvider.getDatabaseManager());
        addAction = new AddAction<>(employeeTablePanel, entityTableModelProvider);
        deleteAction = new DeleteAction(employeeTablePanel.getTable());
        editAction = new EditAction<>(employeeTablePanel, entityTableModelProvider);
        exportAction = new ExportAction(employeeTablePanel, dependencyProvider.getExportService());
        importAction = new ImportAction(dependencyProvider.getImportService(), this::refresh, frame);
        aboutUsAction = new AboutUsAction(frame);

        employeeTablePanel.setComponentPopupMenu(createEmployeeTablePopupMenu());

        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Employees", null, employeeTablePanel, "Employees");
        tabbedPane.addTab("Recipes", Icons.BOOK_ICON, recipeTablePanel, "Recipes");
        tabbedPane.addTab("Ingredients", Icons.INGREDIENTS_ICON, ingredientTablePanel, "Ingredients");
        tabbedPane.addTab("Units", Icons.WEIGHTS_ICON, unitTablePanel, "Units");
        tabbedPane.addTab("Recipe Categories", Icons.CATEGORY_ICON, recipeCategoryTablePanel, "Recipe Categories");

        // TODO this definitely needs to be refactored
        tabbedPane.addChangeListener(e -> {
            EntityTablePanel selectedTablePanel = (EntityTablePanel) tabbedPane.getSelectedComponent();
            addAction.setCurrentTablePanel(selectedTablePanel);
            deleteAction.setCurrentTable(selectedTablePanel.getTable());
            editAction.setCurrentTablePanel(selectedTablePanel);
//            filterAction.setCurrentTable(currentTable);
//            filterAction.drawFilterIcon();
//            cancelFilterAction.setCurrentTable(currentTable);
        });

        // Set up sorting
        var employeeRowSorter = initSorters(employeeTablePanel, recipeTablePanel, ingredientTablePanel, unitTablePanel, recipeCategoryTablePanel);

        // Set up filtering
        var employeeTableFilter = new EmployeeTableFilter(employeeRowSorter);
        var genderFilter = createGenderFilter(employeeTableFilter);
        var departmentFilter = new JScrollPane(createDepartmentFilter(employeeTableFilter, departmentListModel));

        frame.add(tabbedPane, BorderLayout.CENTER);


        frame.add(createToolbar(genderFilter, departmentFilter), BorderLayout.BEFORE_FIRST_LINE);
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setJMenuBar(createMenuBar());
        changeActionsState(0);
    }

    private TableRowSorter<EntityTableModel<Employee>> initSorters(EmployeeTablePanel employeeTablePanel, RecipeTablePanel recipeTablePanel, IngredientTablePanel ingredientTablePanel, UnitTablePanel unitTablePanel, RecipeCategoryTablePanel recipeCategoryTablePanel) {
        var employeeRowSorter = new TableRowSorter<EntityTableModel<Employee>>(employeeTableModel);
        employeeTablePanel.getTable().setRowSorter(employeeRowSorter);
        var recipeRowSorter = new TableRowSorter<EntityTableModel<Recipe>>(recipeTableModel);
        recipeTablePanel.getTable().setRowSorter(recipeRowSorter);
        var ingredientRowSorter = new TableRowSorter<EntityTableModel<Ingredient>>(ingredientTableModel);
        ingredientTablePanel.getTable().setRowSorter(ingredientRowSorter);
        var unitRowSorter = new TableRowSorter<EntityTableModel<Unit>>(unitTableModel);
        unitTablePanel.getTable().setRowSorter(unitRowSorter);
        var recipeCategoryRowSorter = new TableRowSorter<EntityTableModel<RecipeCategory>>(recipeCategoryTableModel);
        recipeCategoryTablePanel.getTable().setRowSorter(recipeCategoryRowSorter);
        return employeeRowSorter;
    }

    private JPanel createSidePanel() {

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(175, frame.getHeight()));
        return sidePanel;
    }

    private EntityTableModel<Employee> createEmployeeTableModel(DependencyProvider dependencyProvider) {
        List<Column<Employee, ?>> columns = List.of(
            Column.readonly("Gender", Gender.class, Employee::getGender),
            Column.readonly("Last name", String.class, Employee::getLastName),
            Column.readonly("First name", String.class, Employee::getFirstName),
            Column.readonly("Birthdate", LocalDate.class, Employee::getBirthDate),
            Column.readonly("Department", Department.class, Employee::getDepartment)
    );
        return new EntityTableModel<>(dependencyProvider.getEmployeeCrudService(), columns);
    }

    private EntityTableModel<Recipe> createRecipeTableModel(DependencyProvider dependencyProvider) {
        List<Column<Recipe, ?>> columns = List.of(
                Column.readonly("Name", String.class, Recipe::getName),
                Column.readonly("Description", String.class, Recipe::getDescription),
                Column.readonly("Preparation Time (min)", int.class, Recipe::getPreparationTime),
                Column.readonly("Nutritional Value (kcal)", int.class, Recipe::getNutritionalValue),
                Column.readonly("Category", RecipeCategory.class, Recipe::getCategory)
        );
        return new EntityTableModel<>(dependencyProvider.getRecipeCrudService(), columns);
    }


    private EntityTableModel<Ingredient> createIngredientTableModel(DependencyProvider dependencyProvider) {
        List<Column<Ingredient, ?>> columns = List.of(
                Column.readonly("Name", String.class, Ingredient::getName),
                Column.readonly("Nutritional Value (kcal)", int.class, Ingredient::getNutritionalValue),
                Column.readonly("Unit", Unit.class, Ingredient::getUnit)
        );
        return new EntityTableModel<>(dependencyProvider.getIngredientCrudService(), columns);
    }

    private EntityTableModel<Unit> createUnitTableModel(DependencyProvider dependencyProvider) {
        List<Column<Unit, ?>> columns = List.of(
                Column.readonly("Name", String.class, Unit::getName),
                Column.readonly("Abbreviation", String.class, Unit::getAbbreviation),
                Column.readonly("Conversion Unit", Unit.class, Unit::getConversionUnit),
                Column.readonly("Conversion Ratio", float.class, Unit::getConversionRatio)
        );
        return new EntityTableModel<>(dependencyProvider.getUnitCrudService(), columns);
    }

    private EntityTableModel<RecipeCategory> createRecipeCategoryTableModel(DependencyProvider dependencyProvider) {
        List<Column<RecipeCategory, ?>> columns = List.of(
                Column.readonly("Name", String.class, RecipeCategory::getName),
                Column.readonly("Color", Color.class, RecipeCategory::getColor)
        );
        return new EntityTableModel<>(dependencyProvider.getRecipeCategoryCrudService(), columns);
    }

    private void refresh() {
        departmentListModel.refresh();
        employeeTableModel.refresh();
        recipeTableModel.refresh();
    }

    private static JList<Either<SpecialFilterDepartmentValues, Department>> createDepartmentFilter(
            EmployeeTableFilter employeeTableFilter, DepartmentListModel departmentListModel) {
        return FilterListModelBuilder.create(SpecialFilterDepartmentValues.class, departmentListModel)
                .setSelectedIndex(0)
                .setVisibleRowsCount(3)
                .setSpecialValuesRenderer(new SpecialFilterDepartmentValuesRenderer())
                .setValuesRenderer(new DepartmentRenderer())
                .setFilter(employeeTableFilter::filterDepartment)
                .build();
    }

    private static JComboBox<Either<SpecialFilterGenderValues, Gender>> createGenderFilter(
            EmployeeTableFilter employeeTableFilter) {
        return FilterComboboxBuilder.create(SpecialFilterGenderValues.class, Gender.values())
                .setSelectedItem(SpecialFilterGenderValues.BOTH)
                .setSpecialValuesRenderer(new SpecialFilterGenderValuesRenderer())
                .setValuesRenderer(new GenderRenderer())
                .setFilter(employeeTableFilter::filterGender)
                .build();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Easy Food Recipe Book");
        frame.setIconImage(((ImageIcon) Icons.APP_ICON).getImage());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private JPopupMenu createEmployeeTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(deleteAction);
        menu.add(editAction);
        menu.add(addAction);
        return menu;
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');
        editMenu.add(addAction);
        editMenu.add(editAction);
        editMenu.add(deleteAction);
        editMenu.addSeparator();
        editMenu.add(quitAction);
        menuBar.add(editMenu);
        editMenu.add(nuclearQuit);
        editMenu.addSeparator();
        editMenu.add(aboutUsAction);
        return menuBar;
    }

    private JToolBar createToolbar(Component... components) {
        var toolbar = new JToolBar();
        toolbar.add(quitAction);
        toolbar.addSeparator();
        toolbar.add(addAction);
        toolbar.add(editAction);
        toolbar.add(deleteAction);
        toolbar.addSeparator();
        toolbar.add(exportAction);
        toolbar.add(importAction);

//        for (var component : components) {
//            toolbar.add(component);
//        }

        return toolbar;
    }

    private void changeActionsState(int selectedItemsCount) {
        editAction.setEnabled(selectedItemsCount == 1);
        deleteAction.setEnabled(selectedItemsCount >= 1);
    }
}
