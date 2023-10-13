package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class AddRecipeAction extends AbstractAction {

    private final CustomTable table;

    public AddRecipeAction(CustomTable table) {
        super("Add recipe", Icons.ADD_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RecipeDialog dialog = new RecipeDialog(new Recipe( "Omacka", "...", 8, 4, "", new RecipeCategory("", Color.BLACK), new HashMap<>()));
        dialog.show(table, "Add Recipe").ifPresent(table::addData);
    }
}
