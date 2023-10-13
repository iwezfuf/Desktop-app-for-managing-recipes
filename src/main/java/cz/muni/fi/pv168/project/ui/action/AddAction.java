package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Department;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.dialog.EmployeeDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.model.EmployeeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class AddAction extends AbstractAction {

    private final CustomTable table;

    public AddAction(CustomTable table) {
        super("Add", Icons.ADD_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Set<RecipeCategory> categorySet = new HashSet<>();
        categorySet.add(new RecipeCategory("Meat", Color.BLACK));

        Set<Integer> ingredientsSet = new HashSet<>();
        ingredientsSet.add(74);

        RecipeDialog dialog = new RecipeDialog(new Recipe(0, "Omacka", "...", 8, 4, "", new RecipeCategory("", Color.BLACK), new HashMap<>()));
        dialog.show(table, "Add Recipe");
    }
}
