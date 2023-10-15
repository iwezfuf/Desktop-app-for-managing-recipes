package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeCategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import jdk.jfr.Category;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class AddCategoryAction extends AbstractAction {

    private final CustomTable table;

    public AddCategoryAction(CustomTable table) {
        super("Add category", Icons.ADD_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Adds new category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_L);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RecipeCategoryDialog dialog = new RecipeCategoryDialog(new RecipeCategory("Category", Color.BLACK));
        dialog.show(table, "Add Category").ifPresent(table::addData); // throws errors until table for ingredients is created
    }
}