package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.action.AddIngredientAction;
import cz.muni.fi.pv168.project.ui.action.AddRecipeAction;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.action.QuitAction;
import cz.muni.fi.pv168.project.ui.model.*;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow {

    private final JFrame frame;

    private final Action quitAction = new QuitAction();
    private final Action addRecipeAction;
    private final Action addIngredientAction;
    private final Action deleteAction;
    private final Action editAction;

    public MainWindow() {

        frame = createFrame();

        var testDataGenerator = new TestDataGenerator();
        var employeeTable = createEmployeeTable(testDataGenerator.createTestEmployees(10));
        var departmentListModel = new DepartmentListModel(testDataGenerator.getDepartments());

        CustomTable<Recipe> recipesTable = new CustomTable<>("My Recipes", new RecipeCellEditor(), new RecipeCellRenderer());

        addRecipeAction = new AddRecipeAction(recipesTable);
        addIngredientAction = new AddIngredientAction(null);
        deleteAction = new DeleteAction(employeeTable);
        editAction = new EditAction(employeeTable, departmentListModel);

        employeeTable.setComponentPopupMenu(createEmployeeTablePopupMenu());

        frame.add(createTabbedPanes(createRecipeTabs(recipesTable)), BorderLayout.CENTER);

        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private List<Tab> createRecipeTabs(CustomTable<Recipe> recipesTable) {

        Map<Ingredient, Integer> ingredients = new HashMap<>();
        ingredients.put(new Ingredient("vejce", 80, new Unit("gram")), 20);
        Recipe r = new Recipe("xd", "xd", 20, 5, "xd", null, ingredients);
        Recipe q = new Recipe("oves", "oves", 48, 1, "-", null, ingredients);

        recipesTable.addData(r);
        recipesTable.addData(q);
        /*table.addComponent(new RecipeTableComponent("Cereal Soup", "Main Dish"));
        table.addComponent(new RecipeTableComponent("Donut", "Sweet Bakery"));
        table.addComponent(new RecipeTableComponent("Scrabbled eggs", "Breakfast"));

        table.addComponent(new RecipeTableComponent("Schnitzel", "Main Dish"));
        table.addComponent(new RecipeTableComponent("Watermelon", "Fruit"));
        table.addComponent(new RecipeTableComponent("Strawberry Milk Shake", "Breakfast"));

        table.addComponent(new RecipeTableComponent("Vepro, knedlo, zelo", "Main Dish"));
        table.addComponent(new RecipeTableComponent("Apple Pie", "Sweet Bakery"));
        table.addComponent(new RecipeTableComponent("Coffee with chocolate ice cream", "Sweet"));*/


        return List.of(
                new Tab(new JScrollPane(recipesTable), "Recipes", Icons.ADD_ICON, "Recipes"),
                new Tab(new JScrollPane(new JTable()), "Ingredients", "Ingredients"),
                new Tab(new JScrollPane(new JTable()), "Units", Icons.EDIT_ICON, "Units")
        );
    }

    private JFrame createFrame() {
        var frame = new JFrame("Easy Food Recipe Book");
        frame.setSize(new Dimension(1920, 1080));
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
