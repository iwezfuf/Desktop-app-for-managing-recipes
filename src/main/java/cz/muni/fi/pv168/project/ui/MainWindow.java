package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.action.AddIngredientAction;
import cz.muni.fi.pv168.project.ui.action.AddRecipeAction;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.action.ExportAction;
import cz.muni.fi.pv168.project.ui.action.ImportAction;
import cz.muni.fi.pv168.project.ui.action.QuitAction;
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
    private final Action addRecipeAction;
    private final Action addIngredientAction;
    private final Action deleteAction;
    private final Action editAction;

    public MainWindow() {

        frame = createFrame();

        var testDataGenerator = new TestDataGenerator();
        var employeeTable = createEmployeeTable(testDataGenerator.createTestEmployees(10));
        var departmentListModel = new DepartmentListModel(testDataGenerator.getDepartments());


        CustomTable<Recipe> recipesTable = new CustomTable<>("My Recipes", new CellEditor(), new CellRenderer(), 130);
        CustomTable<Ingredient> ingredientsTable = new CustomTable<>("My Ingredients", new CellEditor(), new CellRenderer());
        CustomTable<Unit> unitsTable = new CustomTable<>("My Units", new CellEditor(), new CellRenderer());
        CustomTable<RecipeCategory> categoriesTable = new CustomTable<>("My Categories", new CellEditor(), new CellRenderer());

        addRecipeAction = new AddRecipeAction(recipesTable);
        addIngredientAction = new AddIngredientAction(ingredientsTable);
        deleteAction = new DeleteAction(employeeTable);
        editAction = new EditAction(employeeTable, departmentListModel);

        employeeTable.setComponentPopupMenu(createEmployeeTablePopupMenu());

        fillTables(recipesTable, ingredientsTable, unitsTable, categoriesTable); // Only for debugging purposes

        List<Tab> tabs = List.of(
                new Tab(new JScrollPane(recipesTable), "Recipes", Icons.ADD_ICON, "Recipes"),
                new Tab(new JScrollPane(ingredientsTable), "Ingredients", "Ingredients"),
                new Tab(new JScrollPane(unitsTable), "Units", Icons.EDIT_ICON, "Units"),
                new Tab(new JScrollPane(categoriesTable), "Categories", Icons.EDIT_ICON, "Categories")
        );

        frame.add(createTabbedPanes(tabs), BorderLayout.CENTER);
        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());
        frame.setMinimumSize(new Dimension(800, 600));
    }

    public void show() {
        frame.setVisible(true);
    }

    private void fillTables(CustomTable<Recipe> recipesTable,
                            CustomTable<Ingredient> ingredientsTable,
                            CustomTable<Unit> unitsTable,
                            CustomTable<RecipeCategory> categoriesTable) {

        Map<Ingredient, Integer> ingredients = new HashMap<>();

        RecipeCategory breakfast = new RecipeCategory("snidane", Color.orange);
        RecipeCategory lunch = new RecipeCategory("obed", Color.pink);

        categoriesTable.addData(breakfast);
        categoriesTable.addData(lunch);

        Recipe r = new Recipe("Ovesna kase",
                "Ovesná kaše, or Czech oatmeal porridge, is a beloved breakfast dish in Czech cuisine.",
                5,
                2,
                "1. make it",
                breakfast,
                ingredients);
        Recipe q = new Recipe("Rizek", "Traditional healthy dish.", 90, 5, "1. kill pig", lunch, ingredients);
        recipesTable.addData(r);
        recipesTable.addData(q);

        ingredients.put(new Ingredient("vejce", 80, new Unit("gram")), 20);
        ingredientsTable.addData(new Ingredient("vejce", 80, new Unit("gram")));
        ingredientsTable.addData(new Ingredient("muka", 48, new Unit("gram")));
        ingredientsTable.addData(new Ingredient("potato", 48, new Unit("gram")));

        unitsTable.addData(new Unit("gram"));
        unitsTable.addData(new Unit("kilogram"));
        unitsTable.addData(new Unit("liter"));
    }

    private JFrame createFrame() {
        var frame = new JFrame("Easy Food Recipe Book");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private JTable createEmployeeTable(List<Employee> employees) {
        var model = new EmployeeTableModel(employees);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        var genderComboBox = new JComboBox<>(Gender.values());
        table.setDefaultEditor(Gender.class, new DefaultCellEditor(genderComboBox));
        return table;
    }

    private JPopupMenu createEmployeeTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(deleteAction);
        menu.add(editAction);
        return menu;
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');
        editMenu.add(addRecipeAction);
        editMenu.addSeparator();
        editMenu.add(addIngredientAction);
        editMenu.addSeparator();
        editMenu.add(quitAction);
        menuBar.add(editMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.add(quitAction);
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

        return tabbedPane;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        // here you can put the code for handling selection change
    }
}
