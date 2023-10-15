package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
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

public final class AddIngredientAction extends AbstractAction {

    private final CustomTable<Ingredient> table;

    public AddIngredientAction(CustomTable<Ingredient> table) {
        super("Add ingredient", Icons.ADD_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Adds new ingredient");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IngredientDialog dialog = new IngredientDialog(new Ingredient("...", 0, null));
        dialog.show(table, "Add Ingredient").ifPresent(table::addData); // throws errors until table for ingredients is created
    }
}
