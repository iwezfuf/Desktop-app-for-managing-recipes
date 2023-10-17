package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.action.*;
import cz.muni.fi.pv168.project.ui.model.*;
import cz.muni.fi.pv168.project.ui.model.CellEditor;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

    public MainWindow() {
        frame = createFrame();

        CustomTable<Recipe> recipesTable = new CustomTable<>("My Recipes", new CellEditor(), new CellRenderer(), Recipe.class, 130);
        CustomTable<Ingredient> ingredientsTable = new CustomTable<>("My Ingredients", new CellEditor(), new CellRenderer(), Ingredient.class);
        CustomTable<Unit> unitsTable = new CustomTable<>("My Units", new CellEditor(), new CellRenderer(), Unit.class);
        CustomTable<RecipeCategory> categoriesTable = new CustomTable<>("My Categories", new CellEditor(), new CellRenderer(), RecipeCategory.class);

        addAction = new AddAction(recipesTable);
        deleteAction = new DeleteAction(recipesTable);

        fillTables(recipesTable, ingredientsTable, unitsTable, categoriesTable); // Only for debugging purposes

        List<Tab> tabs = List.of(
                new Tab(new JScrollPane(recipesTable), "Recipes", Icons.BOOK_ICON, "Recipes"),
                new Tab(new JScrollPane(ingredientsTable), "Ingredients", Icons.FRIDGE_ICON, "Ingredients"),
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

        RecipeCategory breakfast = new RecipeCategory("snidane", Color.orange);
        RecipeCategory lunch = new RecipeCategory("obed", Color.pink);

        Unit gram = new Unit("gram", null, 1);
        Unit kilogram = new Unit("kilogram", gram, 1000);

        Ingredient oatmeal = new Ingredient("oatmeal", 100, gram);
        Ingredient milk = new Ingredient("milk", 100, gram);
        Ingredient breadCrumbs = new Ingredient("bread crumbs", 100, gram);

        Map<Ingredient, Integer> ingredientsOatMeal = new HashMap<>();
        ingredientsOatMeal.put(oatmeal, 100);
        ingredientsOatMeal.put(milk, 100);

        Map<Ingredient, Integer> ingredientsRizek = new HashMap<>();
        ingredientsRizek.put(breadCrumbs, 100);


        Recipe r = new Recipe("Ovesna kase",
                "Ovesná kaše, or Czech oatmeal porridge, is a beloved breakfast dish in Czech cuisine.",
                5,
                2,
                "1. make it",
                breakfast,
                ingredientsOatMeal);
        Recipe q = new Recipe("Rizek", "Traditional healthy dish.", 90, 5, "1. kill pig", lunch, ingredientsRizek);
        recipesTable.addData(r);
        recipesTable.addData(q);

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
        editMenu.add(deleteAction);
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
        toolbar.add(deleteAction);
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
        });

        return tabbedPane;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        // here you can put the code for handling selection change
    }
}
