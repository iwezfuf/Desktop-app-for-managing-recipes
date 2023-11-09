package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.model.AbstractFilter;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientFilterDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeFilterDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.model.UserItemClasses;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Represents an action which filters the custom table entities.
 */
public class FilterAction extends AbstractAction {

    private CustomTable table;
    private final String name;
    public static HashMap<Class<? extends EntityDialog>, AbstractFilter> filterMap;

    public FilterAction(String name, CustomTable<? extends Entity> table) {

        super(name, Icons.FILTER_ICON);
        this.table = table;
        this.name = name;

        filterMap = new HashMap<>();
        filterMap.put(RecipeFilterDialog.class, null);
        filterMap.put(IngredientFilterDialog.class, null);
        putValue(SHORT_DESCRIPTION, "Filters data");
        putValue(MNEMONIC_KEY, KeyEvent.VK_F);
    }

    public void setCurrentTable(CustomTable<? extends Entity> table) {
        this.table = table;
    }

    public void drawFilterIcon() {
        if (table.isActiveFilter()) {
            putValue(SMALL_ICON, Icons.ACTIVE_FILTER);
        } else {
            putValue(SMALL_ICON, Icons.FILTER_ICON);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        EntityDialog<AbstractFilter> dialog;
        Class<?> tableClass = table.getTypeParameterClass();
        Class<? extends EntityDialog> dialogClass = UserItemClasses.filterDialogMap.get(tableClass);
        try {
            dialog = dialogClass.getConstructor(AbstractFilter.class).newInstance(filterMap.get(dialogClass));

        } catch (Exception exc) {
            throw new RuntimeException("Failed to create dialog.", exc);
        }

        dialog.showWithCustomButtonText(null, "Filter",
                new Object[]{ "Apply filters", "Discard changes in the tab"}, "Apply filters")
                .ifPresent(a -> {

                    filterMap.put(dialogClass, a);
                    table.applyFilter(a);
                    drawFilterIcon();
                });
    }
}
