package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.action.*;
import cz.muni.fi.pv168.project.ui.model.*;
import cz.muni.fi.pv168.project.ui.model.CellEditor;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow {

    private final JFrame frame;

    private final Action quitAction = new QuitAction();
    private final Action importAction = new ImportAction();
    private final Action exportAction = new ExportAction();

    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final FilterAction filterAction;
    private final CancelFilterAction cancelFilterAction;

    public MainWindow() {
        frame = createFrame();

        CustomTable<Recipe> recipesTable = new CustomTable<>("My Recipes", new CellEditor(), new CellRenderer(), Recipe.class, 130);
        CustomTable<Ingredient> ingredientsTable = new CustomTable<>("My Ingredients", new CellEditor(), new CellRenderer(), Ingredient.class);
        CustomTable<Unit> unitsTable = new CustomTable<>("My Units", new CellEditor(), new CellRenderer(), Unit.class);
        CustomTable<RecipeCategory> categoriesTable = new CustomTable<>("My Categories", new CellEditor(), new CellRenderer(), RecipeCategory.class);

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

        Map<Ingredient, Integer> ingredientsPancakes = new HashMap<>();
        ingredientsPancakes.put(flour, 250);
        ingredientsPancakes.put(milk, 250);
        ingredientsPancakes.put(egg, 2);
        ingredientsPancakes.put(oil, 1);
        ingredientsPancakes.put(salt, 1);
        ingredientsPancakes.put(sugar, 1);

        String pancakesInstructions = "1. Mix all ingredients together.\n" +
                "2. Fry pancakes on a pan.\n" +
                "3. Serve with jam, honey, chocolate or whatever you like.";


        Recipe pancakes = new Recipe("Panckes", "Traditional healthy dish.", 90, 5, pancakesInstructions, breakfast, ingredientsPancakes);
        recipesTable.addData(pancakes);

        Map<Ingredient, Integer> ingredientsSpaghettiAglioOlio = new HashMap<>();
        ingredientsSpaghettiAglioOlio.put(spaghetti, 200);
        ingredientsSpaghettiAglioOlio.put(oliveOil, 3);
        ingredientsSpaghettiAglioOlio.put(garlic, 4);
        ingredientsSpaghettiAglioOlio.put(redPepperFlakes, 1);
        ingredientsSpaghettiAglioOlio.put(parsley, 1);
        ingredientsSpaghettiAglioOlio.put(salt, 1);

        String spaghettiAglioOlioInstructions = "1. Boil spaghetti in salted water until al dente. Drain and set aside.\n" +
                "2. In a pan, heat olive oil over low heat. Add minced garlic and red pepper flakes. Cook until garlic is fragrant but not browned.\n" +
                "3. Toss the cooked spaghetti in the garlic and oil mixture.\n" +
                "4. Season with salt and garnish with chopped parsley.\n" +
                "5. Serve hot and optionally sprinkle with grated Parmesan cheese.";

        Recipe spaghettiAglioOlio = new Recipe("Spaghetti Aglio e Olio", "A simple and classic Italian pasta dish.", 20, 2, spaghettiAglioOlioInstructions, lunch, ingredientsSpaghettiAglioOlio);
        recipesTable.addData(spaghettiAglioOlio);


        Map<Ingredient, Integer> ingredientsChickenTaco = new HashMap<>();
        ingredientsChickenTaco.put(chickenBreast, 2);
        ingredientsChickenTaco.put(tacoSeasoning, 1);
        ingredientsChickenTaco.put(tortillas, 8);
        ingredientsChickenTaco.put(salsa, 1);
        ingredientsChickenTaco.put(cheese, 1);
        ingredientsChickenTaco.put(lettuce, 1);
        ingredientsChickenTaco.put(tomato, 2);
        ingredientsChickenTaco.put(avocado, 1);

        String chickenTacoInstructions = "1. Season chicken breasts with taco seasoning and grill or cook in a skillet until fully cooked.\n" +
                "2. While the chicken is cooking, warm the tortillas in a dry skillet or microwave.\n" +
                "3. Slice the cooked chicken into thin strips.\n" +
                "4. Assemble the tacos: place chicken strips, salsa, cheese, lettuce, diced tomatoes, and sliced avocado on each tortilla.\n" +
                "5. Fold the tortillas and serve immediately.";

        Recipe chickenTaco = new Recipe("Chicken Tacos", "A delicious and satisfying taco recipe.", 30, 4, chickenTacoInstructions, dinner, ingredientsChickenTaco);
        recipesTable.addData(chickenTaco);



        Map<Ingredient, Integer> ingredientsOatMeal = new HashMap<>();
        ingredientsOatMeal.put(oatmeal, 100);
        ingredientsOatMeal.put(milk, 100);

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
            CustomTable<? extends AbstractUserItemData> currentTable = (CustomTable<? extends AbstractUserItemData>) (((JViewport) selectedComponent.getComponent(0)).getComponent(0));
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
