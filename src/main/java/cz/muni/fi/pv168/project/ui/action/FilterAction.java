package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.AbstractUserItemData;
import cz.muni.fi.pv168.project.ui.dialog.RecipeFilterDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FilterAction extends AbstractAction {

    private CustomTable table;
    private String name;

    public FilterAction(String name, CustomTable<? extends AbstractUserItemData> table) {

        super(name, Icons.FILTER_ICON);
        this.table = table;
        this.name = name;
        putValue(SHORT_DESCRIPTION, "Filters data");
        putValue(MNEMONIC_KEY, KeyEvent.VK_F);
    }

    public void setCurrentTable(CustomTable<? extends AbstractUserItemData> table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        RecipeFilterDialog dialog = new RecipeFilterDialog();
        dialog.show(null, name + " Filter").ifPresent(a -> table.applyFilter(a));
    }
}
