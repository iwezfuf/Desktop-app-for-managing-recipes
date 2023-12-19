package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.ui.action.AboutUsAction;
import cz.muni.fi.pv168.project.ui.action.AddAction;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.action.ExportAction;
import cz.muni.fi.pv168.project.ui.action.ImportAction;
import cz.muni.fi.pv168.project.ui.action.NuclearQuitAction;
import cz.muni.fi.pv168.project.ui.action.QuitAction;
import cz.muni.fi.pv168.project.ui.action.ViewAction;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeCategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;
import cz.muni.fi.pv168.project.ui.model.Column;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.panels.EntityTablePanel;
import cz.muni.fi.pv168.project.ui.panels.IngredientTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeCategoryTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;
import cz.muni.fi.pv168.project.ui.panels.UnitTablePanel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainWindow {

    private final JFrame frame;
    private final Action quitAction = new QuitAction();
    private final Action nuclearQuit;
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final ViewAction viewAction;
    private final Action exportAction;
    private final Action importAction;
    private final AboutUsAction aboutUsAction;
    private final EntityTableModel<Recipe> recipeTableModel;
    private final EntityTableModel<Ingredient> ingredientTableModel;
    private final EntityTableModel<Unit> unitTableModel;
    private final EntityTableModel<RecipeCategory> recipeCategoryTableModel;
    private final EntityTableModelProviderWithCrud entityTableModelProviderWithCrud;

    public MainWindow(DependencyProvider dependencyProvider) {

        frame = createFrame();

        recipeTableModel = createRecipeTableModel(dependencyProvider);
        ingredientTableModel = createIngredientTableModel(dependencyProvider);
        unitTableModel = createUnitTableModel(dependencyProvider);
        recipeCategoryTableModel = createRecipeCategoryTableModel(dependencyProvider);

        entityTableModelProviderWithCrud = new EntityTableModelProviderWithCrud(
                recipeTableModel,
                ingredientTableModel,
                unitTableModel,
                recipeCategoryTableModel,
                dependencyProvider.getRecipeIngredientAmountCrudService()
        );

        // Only run this once to fill the database with test data
        if (recipeTableModel.getRowCount() == 0
                && ingredientTableModel.getRowCount() == 0
                && unitTableModel.getRowCount() == 3 // number of base units
                && recipeCategoryTableModel.getRowCount() == 1) { // no category
            TestDataGenerator testDataGenerator = new TestDataGenerator();
            testDataGenerator.fillTables(entityTableModelProviderWithCrud);
        }

        Validator<Recipe> recipeValidator = dependencyProvider.getRecipeValidator();
        Validator<Ingredient> ingredientValidator = dependencyProvider.getIngredientValidator();
        Validator<Unit> unitValidator = dependencyProvider.getUnitValidator();
        Validator<RecipeCategory> recipeCategoryValidator = dependencyProvider.getRecipeCategoryValidator();

        var recipeTablePanel = new RecipeTablePanel(recipeTableModel, recipeValidator, RecipeDialog.class, this::changeActionsState, entityTableModelProviderWithCrud);
        var ingredientTablePanel = new IngredientTablePanel(ingredientTableModel, ingredientValidator, IngredientDialog.class, this::changeActionsState, entityTableModelProviderWithCrud);
        var unitTablePanel = new UnitTablePanel(unitTableModel, unitValidator, UnitDialog.class, this::changeActionsState);
        var recipeCategoryTablePanel = new RecipeCategoryTablePanel(recipeCategoryTableModel, recipeCategoryValidator, RecipeCategoryDialog.class, this::changeActionsState);

        nuclearQuit = new NuclearQuitAction(dependencyProvider.getDatabaseManager());
        addAction = new AddAction<>(recipeTablePanel, entityTableModelProviderWithCrud);
        deleteAction = new DeleteAction(recipeTablePanel.getTable());
        editAction = new EditAction<>(recipeTablePanel, entityTableModelProviderWithCrud);
        viewAction = new ViewAction<>(recipeTablePanel, entityTableModelProviderWithCrud);
        exportAction = new ExportAction(recipeTablePanel, dependencyProvider.getExportService());
        importAction = new ImportAction(dependencyProvider.getImportService(), this::refresh, frame);
        aboutUsAction = new AboutUsAction(frame);


        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Recipes", Icons.BOOK_ICON, recipeTablePanel, "Recipes");
        tabbedPane.addTab("Ingredients", Icons.INGREDIENTS_ICON, ingredientTablePanel, "Ingredients");
        tabbedPane.addTab("Units", Icons.WEIGHTS_ICON, unitTablePanel, "Units");
        tabbedPane.addTab("Recipe Categories", Icons.CATEGORY_ICON, recipeCategoryTablePanel, "Recipe Categories");

        tabbedPane.addChangeListener(e -> {
            EntityTablePanel selectedTablePanel = (EntityTablePanel) tabbedPane.getSelectedComponent();
            addAction.setCurrentTablePanel(selectedTablePanel);
            deleteAction.setCurrentTable(selectedTablePanel.getTable());
            editAction.setCurrentTablePanel(selectedTablePanel);
            viewAction.setCurrentTablePanel(selectedTablePanel);
            refresh();
        });

        // Set up sorting
        initSorters(recipeTablePanel, ingredientTablePanel, unitTablePanel, recipeCategoryTablePanel);

        frame.add(tabbedPane, BorderLayout.CENTER);


        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setJMenuBar(createMenuBar());
        changeActionsState(0);
    }

    private void initSorters(RecipeTablePanel recipeTablePanel, IngredientTablePanel ingredientTablePanel, UnitTablePanel unitTablePanel, RecipeCategoryTablePanel recipeCategoryTablePanel) {
        var recipeRowSorter = new TableRowSorter<EntityTableModel<Recipe>>(recipeTableModel);
        recipeTablePanel.getTable().setRowSorter(recipeRowSorter);
        var ingredientRowSorter = new TableRowSorter<EntityTableModel<Ingredient>>(ingredientTableModel);
        ingredientTablePanel.getTable().setRowSorter(ingredientRowSorter);
        var unitRowSorter = new TableRowSorter<EntityTableModel<Unit>>(unitTableModel);
        unitTablePanel.getTable().setRowSorter(unitRowSorter);
        var recipeCategoryRowSorter = new TableRowSorter<EntityTableModel<RecipeCategory>>(recipeCategoryTableModel);
        recipeCategoryTablePanel.getTable().setRowSorter(recipeCategoryRowSorter);

        // Sort by first column on startup
        recipeRowSorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        recipeRowSorter.sort();
        ingredientRowSorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        ingredientRowSorter.sort();
        unitRowSorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        unitRowSorter.sort();
        recipeCategoryRowSorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        recipeCategoryRowSorter.sort();
    }

    private EntityTableModel<Recipe> createRecipeTableModel(DependencyProvider dependencyProvider) {
        List<Column<Recipe, ?>> columns = List.of(
                Column.readonly("Name", String.class, Recipe::getName),
                Column.readonly("Number of servings", int.class, Recipe::getNumOfServings),
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
                Column.readonly("Unit", Unit.class, Ingredient::getUnit),
                Column.readonly("Recipes Count", String.class, ingredient -> ingredient.getRecipeCountPercentage(recipeTableModel.getEntities()))
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
        recipeTableModel.refresh();
        ingredientTableModel.refresh();
        unitTableModel.refresh();
        recipeCategoryTableModel.refresh();
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

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("Menu");
        editMenu.setMnemonic('e');
        editMenu.add(addAction);
        editMenu.add(editAction);
        editMenu.add(viewAction);
        editMenu.add(deleteAction);
        editMenu.addSeparator();
        editMenu.add(quitAction);
        menuBar.add(editMenu);

        if(Objects.equals(System.getProperty("isDeveloperVersion"), "true")) {
            editMenu.add(nuclearQuit);
            editMenu.addSeparator();
        }

        editMenu.add(aboutUsAction);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.add(quitAction);
        toolbar.addSeparator();
        toolbar.add(addAction);
        toolbar.add(editAction);
        toolbar.add(viewAction);
        toolbar.add(deleteAction);
        toolbar.addSeparator();
        toolbar.add(exportAction);
        toolbar.add(importAction);
        return toolbar;
    }

    private void changeActionsState(int selectedItemsCount) {
        // one base unit / category was chosen
        if (selectedItemsCount == -1) {
            editAction.setEnabled(false);
            deleteAction.setEnabled(false);
            return;
        }

        editAction.setEnabled(selectedItemsCount == 1);
        deleteAction.setEnabled(selectedItemsCount >= 1);
        viewAction.setEnabled(selectedItemsCount == 1);
    }
}
