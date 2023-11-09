package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditAction extends AbstractAction {
    private CustomTable table;

    public EditAction(CustomTable<? extends Entity> table) {
        super("Edit", Icons.EDIT_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Edits selected item");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    public void setCurrentTable(CustomTable<? extends Entity> table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        table.editSelectedCell();
    }
}
