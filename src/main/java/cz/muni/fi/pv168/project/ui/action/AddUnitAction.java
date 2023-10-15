package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddUnitAction extends AbstractAction {

    private final CustomTable table;

    public AddUnitAction(CustomTable table) {
        super("Add unit", Icons.ADD_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Adds new unity");
        putValue(MNEMONIC_KEY, KeyEvent.VK_U);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UnitDialog dialog = new UnitDialog(new Unit("Name", null, -1));
        dialog.show(table, "Add Unity").ifPresent(table::addData); // throws errors until table for ingredients is created
    }
}
