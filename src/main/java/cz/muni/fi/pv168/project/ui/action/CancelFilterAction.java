package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.AbstractUserItemData;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CancelFilterAction extends AbstractAction {

    private CustomTable table;
    private String name;

    public CancelFilterAction(String name, CustomTable<? extends AbstractUserItemData> table) {
        super("Cancel " + name + " Filter", Icons.CANCEL_FILTER_ICON);
        putValue(SHORT_DESCRIPTION, "Cancels filter");
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.table.cancelFilter();
    }
}