package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Employee;
import cz.muni.fi.pv168.project.model.Gender;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.action.AddAction;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.action.QuitAction;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.model.EmployeeTableModel;
import cz.muni.fi.pv168.project.ui.model.DepartmentListModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableComponent;
import cz.muni.fi.pv168.project.ui.model.Tab;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class MainWindow {

    private final JFrame frame;

    private final Action quitAction = new QuitAction();
    private final Action addAction;
    private final Action deleteAction;
    private final Action editAction;

    public MainWindow() {

        frame = createFrame();

        var testDataGenerator = new TestDataGenerator();
        var employeeTable = createEmployeeTable(testDataGenerator.createTestEmployees(10));
        var departmentListModel = new DepartmentListModel(testDataGenerator.getDepartments());

        CustomTable recipesTable = new CustomTable("My Recipes");

        addAction = new AddAction(recipesTable);
        deleteAction = new DeleteAction(employeeTable);
        editAction = new EditAction(employeeTable, departmentListModel);

        employeeTable.setComponentPopupMenu(createEmployeeTablePopupMenu());

        frame.add(createTabbedPanes(createTabs(recipesTable)), BorderLayout.CENTER);

        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private List<Tab> createTabs(CustomTable recipesTable) {

        Recipe r = new Recipe(0, "xd", "xd", 20, 5, "xd", null, Set.of(1));
        Recipe q = new Recipe(0, "oves", "oves", 48, 1, "-", null, Set.of(1));

        recipesTable.addComponent(new RecipeTableComponent(r));
        recipesTable.addComponent(new RecipeTableComponent(q));
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
        editMenu.add(addAction);
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
