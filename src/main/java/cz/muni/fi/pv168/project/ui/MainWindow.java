package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCrudService;
import cz.muni.fi.pv168.project.business.service.crud.UnitCrudService;
import cz.muni.fi.pv168.project.business.service.export.GenericExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericImportService;
import cz.muni.fi.pv168.project.business.service.export.JSONBatchExporter;
import cz.muni.fi.pv168.project.business.service.export.JSONBatchImporter;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.action.*;
import cz.muni.fi.pv168.project.ui.model.*;
import cz.muni.fi.pv168.project.ui.model.CellEditor;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow {

    private final JFrame frame = createFrame();

    InMemoryRepository<Recipe> recipeRepository = new InMemoryRepository<>(List.of());
    InMemoryRepository<Ingredient> ingredientRepository = new InMemoryRepository<>(List.of());
    InMemoryRepository<Unit> unitRepository = new InMemoryRepository<>(List.of());
    InMemoryRepository<RecipeCategory> categoryRepository = new InMemoryRepository<>(List.of());

    RecipeValidator employeeValidator = new RecipeValidator();

    UuidGuidProvider guidProvider = new UuidGuidProvider();
    CrudService<Recipe> recipeCrudService = new RecipeCrudService(recipeRepository, employeeValidator, guidProvider);
    CrudService<Ingredient> ingredientCrudService = new IngredientCrudService(ingredientRepository, guidProvider);
    CrudService<Unit> unitCrudService = new UnitCrudService(unitRepository, guidProvider);
    CrudService<RecipeCategory> categoryCrudService = new RecipeCategoryCrudService(categoryRepository, guidProvider);
    GenericExportService exportService = new GenericExportService(ingredientCrudService, recipeCrudService, unitCrudService, categoryCrudService, List.of(new JSONBatchExporter()));
    GenericImportService importService = new GenericImportService(ingredientCrudService, recipeCrudService, unitCrudService, categoryCrudService, List.of(new JSONBatchImporter()));

    private final Action quitAction = new QuitAction();
    private final Action importAction = new ImportAction(importService, this::refresh, frame);
    private final Action exportAction = new ExportAction(frame, exportService);
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final FilterAction filterAction;
    private final CancelFilterAction cancelFilterAction;
    private final CustomTable<Recipe> recipesTable;
    private final CustomTable<Ingredient> ingredientsTable;
    private final CustomTable<Unit> unitsTable;
    private final CustomTable<RecipeCategory> categoriesTable;


    public MainWindow() {
        recipesTable = new CustomTable<Recipe>("My Recipes", new CellEditor(), new CellRenderer(), Recipe.class, recipeCrudService, 130);
        ingredientsTable = new CustomTable<Ingredient>("My Ingredients", new CellEditor(), new CellRenderer(), Ingredient.class, ingredientCrudService);
        unitsTable = new CustomTable<Unit>("My Units", new CellEditor(), new CellRenderer(), Unit.class, unitCrudService);
        categoriesTable = new CustomTable<RecipeCategory>("My Categories", new CellEditor(), new CellRenderer(), RecipeCategory.class, categoryCrudService);

        addAction = new AddAction(recipesTable);
        deleteAction = new DeleteAction(recipesTable);
        editAction = new EditAction(recipesTable);
        filterAction = new FilterAction("", recipesTable);
        cancelFilterAction = new CancelFilterAction("", recipesTable);

        fillTables(recipesTable, ingredientsTable, unitsTable, categoriesTable); // Only for debugging purposes

        List<Tab> tabs = List.of(
                new Tab(new JScrollPane(recipesTable), "Recipes", Icons.BOOK_ICON, "Recipes"),
                new Tab(new JScrollPane(ingredientsTable), "Ingredients", Icons.INGREDIENTS_ICON, "Ingredients"),
                new Tab(new JScrollPane(unitsTable), "Units", Icons.WEIGHTS_ICON, "Units"),
                new Tab(new JScrollPane(categoriesTable), "Categories", Icons.CATEGORY_ICON, "Categories")
        );

        frame.add(createTabbedPanes(tabs), BorderLayout.CENTER);
        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());
        frame.setMinimumSize(new Dimension(1200, 600));
    }

    public void show() {
        frame.setVisible(true);
    }

    private void refresh(ImportType importType) {
        recipesTable.refresh(importType);
        ingredientsTable.refresh(importType);
        unitsTable.refresh(importType);
        categoriesTable.refresh(importType);
    }

    private void fillTables(CustomTable<Recipe> recipesTable,
                            CustomTable<Ingredient> ingredientsTable,
                            CustomTable<Unit> unitsTable,
                            CustomTable<RecipeCategory> categoriesTable) {

        RecipeCategory breakfast = new RecipeCategory("breakfast", Color.orange);
        RecipeCategory lunch = new RecipeCategory("lunch", Color.pink);
        RecipeCategory dinner = new RecipeCategory("dinner", Color.green);
        RecipeCategory dessert = new RecipeCategory("dessert", Color.red);
        RecipeCategory snack = new RecipeCategory("snack", Color.yellow);
        RecipeCategory drink = new RecipeCategory("drink", Color.blue);

        Unit gram = new Unit("gram", null, 1, "g");
        Unit kilogram = new Unit("kilogram", gram, 1000, "kg");
        Unit milliliter = new Unit("milliliter", null, 1, "ml");
        Unit liter = new Unit("liter", milliliter, 1000, "l");
        Unit deciliter = new Unit("deciliter", milliliter, 100, "dl");
        Unit piece = new Unit("piece", null, 1, "pcs");
        Unit dozen = new Unit("dozen", piece, 12, "pcs");
        Unit teaspoon = new Unit("teaspoon", milliliter, 5, "tsp");
        Unit tablespoon = new Unit("tablespoon", milliliter, 15, "tbsp");
        Unit cup = new Unit("cup", milliliter, 250, "cup");

        Ingredient oatmeal = new Ingredient("oatmeal", 4, gram);
        Ingredient milk = new Ingredient("milk", 65, liter);
        Ingredient pork = new Ingredient("pork", 100, kilogram);
        Ingredient egg = new Ingredient("egg", 1, piece);
        Ingredient flour = new Ingredient("flour", 1, kilogram);
        Ingredient oil = new Ingredient("oil", 1, liter);
        Ingredient salt = new Ingredient("salt", 1, gram);
        Ingredient pepper = new Ingredient("pepper", 1, gram);
        Ingredient garlic = new Ingredient("garlic", 1, piece);
        Ingredient onion = new Ingredient("onion", 1, piece);
        Ingredient potato = new Ingredient("potato", 1, piece);
        Ingredient carrot = new Ingredient("carrot", 1, piece);
        Ingredient parsley = new Ingredient("parsley", 1, piece);
        Ingredient tomato = new Ingredient("tomato", 1, piece);
        Ingredient cucumber = new Ingredient("cucumber", 1, piece);
        Ingredient cheese = new Ingredient("cheese", 1, kilogram);
        Ingredient ham = new Ingredient("ham", 1, kilogram);
        Ingredient butter = new Ingredient("butter", 1, kilogram);
        Ingredient sugar = new Ingredient("sugar", 1, kilogram);
        Ingredient spaghetti = new Ingredient("spaghetti", 1, kilogram);
        Ingredient oliveOil = new Ingredient("olive oil", 1, liter);
        Ingredient redPepperFlakes = new Ingredient("red pepper flakes", 1, teaspoon);
        Ingredient salsa = new Ingredient("salsa", 1, cup);
        Ingredient lettuce = new Ingredient("lettuce", 1, piece);
        Ingredient avocado = new Ingredient("avocado", 1, piece);
        Ingredient chickenBreast = new Ingredient("chicken breast", 1, kilogram);
        Ingredient tacoSeasoning = new Ingredient("taco seasoning", 1, piece);
        Ingredient tortillas = new Ingredient("tortillas", 1, piece);

        ArrayList<RecipeIngredientAmount> ingredientsPancakes = new ArrayList<>();
        ingredientsPancakes.add(new RecipeIngredientAmount(flour, 250));
        ingredientsPancakes.add(new RecipeIngredientAmount(milk, 250));
        ingredientsPancakes.add(new RecipeIngredientAmount(egg, 2));
        ingredientsPancakes.add(new RecipeIngredientAmount(oil, 1));
        ingredientsPancakes.add(new RecipeIngredientAmount(salt, 1));
        ingredientsPancakes.add(new RecipeIngredientAmount(sugar, 1));

        String pancakesInstructions = "1. Mix all ingredients together.\n" +
                "2. Fry pancakes on a pan.\n" +
                "3. Serve with jam, honey, chocolate or whatever you like.";


        Recipe pancakes = new Recipe("Panckes", "Traditional healthy dish.", 90, 5, pancakesInstructions, breakfast, ingredientsPancakes);
        recipesTable.addData(pancakes);

        ArrayList<RecipeIngredientAmount> ingredientsSpaghettiAglioOlio = new ArrayList<>();
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghetti, 200));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(oliveOil, 3));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(garlic, 4));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(redPepperFlakes, 1));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(parsley, 1));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(salt, 1));

        String spaghettiAglioOlioInstructions = "1. Boil spaghetti in salted water until al dente. Drain and set aside.\n" +
                "2. In a pan, heat olive oil over low heat. Add minced garlic and red pepper flakes. Cook until garlic is fragrant but not browned.\n" +
                "3. Toss the cooked spaghetti in the garlic and oil mixture.\n" +
                "4. Season with salt and garnish with chopped parsley.\n" +
                "5. Serve hot and optionally sprinkle with grated Parmesan cheese.";

        Recipe spaghettiAglioOlio = new Recipe("Spaghetti Aglio e Olio", "A simple and classic Italian pasta dish.", 20, 2, spaghettiAglioOlioInstructions, lunch, ingredientsSpaghettiAglioOlio);
        recipesTable.addData(spaghettiAglioOlio);


        ArrayList<RecipeIngredientAmount> ingredientsChickenTaco = new ArrayList<>();
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenBreast, 2));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(tacoSeasoning, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(tortillas, 8));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(salsa, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(cheese, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(lettuce, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(tomato, 2));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(avocado, 1));

        String chickenTacoInstructions = "1. Season chicken breasts with taco seasoning and grill or cook in a skillet until fully cooked.\n" +
                "2. While the chicken is cooking, warm the tortillas in a dry skillet or microwave.\n" +
                "3. Slice the cooked chicken into thin strips.\n" +
                "4. Assemble the tacos: place chicken strips, salsa, cheese, lettuce, diced tomatoes, and sliced avocado on each tortilla.\n" +
                "5. Fold the tortillas and serve immediately.";

        Recipe chickenTaco = new Recipe("Chicken Tacos", "A delicious and satisfying taco recipe.", 30, 4, chickenTacoInstructions, dinner, ingredientsChickenTaco);
        recipesTable.addData(chickenTaco);



        ArrayList<RecipeIngredientAmount> ingredientsOatMeal = new ArrayList<>();
        ingredientsOatMeal.add(new RecipeIngredientAmount(oatmeal, 100));
        ingredientsOatMeal.add(new RecipeIngredientAmount(milk, 100));

        Recipe r = new Recipe("Ovesna kase",
                "Ovesná kaše, or Czech oatmeal porridge, is a beloved breakfast dish in Czech cuisine.",
                5,
                2,
                "1. make it",
                breakfast,
                ingredientsOatMeal);

        recipesTable.addData(r);


        for (Ingredient ingredient : Ingredient.getListOfIngredients()) {
            ingredientsTable.addData(ingredient);
        }

        for (Unit unit : Unit.getListOfUnits()) {
            unitsTable.addData(unit);
        }

        for (RecipeCategory category : RecipeCategory.getListOfCategories()) {
            categoriesTable.addData(category);
        }
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
        var editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');

        editMenu.add(addAction);
        editMenu.addSeparator();
        editMenu.add(editAction);
        editMenu.addSeparator();
        editMenu.add(deleteAction);
        editMenu.addSeparator();
        editMenu.add(importAction);
        editMenu.addSeparator();
        editMenu.add(exportAction);
        editMenu.addSeparator();
        editMenu.add(quitAction);
        menuBar.add(editMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.add(quitAction);
        toolbar.addSeparator();
        toolbar.add(addAction);
        toolbar.addSeparator();
        toolbar.add(editAction);
        toolbar.addSeparator();
        toolbar.add(deleteAction);
        toolbar.addSeparator();
        toolbar.add(filterAction);
        toolbar.addSeparator();
        toolbar.add(cancelFilterAction);
        toolbar.addSeparator();
        toolbar.add(importAction);
        toolbar.addSeparator();
        toolbar.add(exportAction);
        return toolbar;
    }

    private JTabbedPane createTabbedPanes(List<Tab> tabbedPanesList) {

        JTabbedPane tabbedPane = new JTabbedPane();
        for (Tab tab : tabbedPanesList) {
            tabbedPane.addTab(tab.getName(), tab.getIcon(), tab.getComponent(), tab.getTooltip());
        }

        tabbedPane.addChangeListener(e -> {
            JScrollPane selectedComponent = (JScrollPane) tabbedPane.getSelectedComponent();
            CustomTable<? extends Entity> currentTable = (CustomTable<? extends Entity>) (((JViewport) selectedComponent.getComponent(0)).getComponent(0));
            addAction.setCurrentTable(currentTable);
            deleteAction.setCurrentTable(currentTable);
            filterAction.setCurrentTable(currentTable);
            cancelFilterAction.setCurrentTable(currentTable);
        });

        return tabbedPane;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        // here you can put the code for handling selection change
    }
}
