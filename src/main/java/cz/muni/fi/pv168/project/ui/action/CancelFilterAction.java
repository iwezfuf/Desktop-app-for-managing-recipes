package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.model.AbstractFilter;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.model.UserItemClasses;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CancelFilterAction extends AbstractAction {

    private CustomTable table;
    private String name;
    private FilterAction filterAction;

    public CancelFilterAction(String name, CustomTable<? extends Entity> table, FilterAction filterAction) {
        super("Cancel " + name + " Filter", Icons.CANCEL_FILTER_ICON);
        putValue(SHORT_DESCRIPTION, "Cancels filter");
        this.table = table;
        this.filterAction = filterAction;
    }

    public void setCurrentTable(CustomTable<? extends Entity> table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        EntityDialog<AbstractFilter> dialog;
        Class<?> tableClass = table.getTypeParameterClass();
        Class<? extends EntityDialog> dialogClass = UserItemClasses.filterDialogMap.get(tableClass);
        FilterAction.filterMap.remove(dialogClass);
        this.table.cancelFilter();
        this.filterAction.putValue(SMALL_ICON, Icons.FILTER_ICON);
    }
}