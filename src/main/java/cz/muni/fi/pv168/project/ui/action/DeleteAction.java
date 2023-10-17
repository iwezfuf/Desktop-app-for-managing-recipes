package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.AbstractUserItemData;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class DeleteAction extends AbstractAction {

    private CustomTable table;

    public DeleteAction(CustomTable<? extends AbstractUserItemData> table) {
        super("Delete", Icons.DELETE_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Deletes selected items");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    public void setCurrentTable(CustomTable<? extends AbstractUserItemData> table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        table.deleteSelectedCells();
    }
}
