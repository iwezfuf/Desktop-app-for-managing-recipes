package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.CustomTable;
import cz.muni.fi.pv168.project.ui.model.UserItemClasses;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddAction extends AbstractAction {
    private CustomTable table;

    public AddAction(CustomTable<? extends Entity> table) {
        super("Add", Icons.ADD_ICON);
        this.table = table;
        putValue(SHORT_DESCRIPTION, "Adds new item");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    public void setCurrentTable(CustomTable<? extends Entity> table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EntityDialog<Entity> dialog;
        Class<?> tableClass = table.getTypeParameterClass();
        Class<? extends EntityDialog> dialogClass = UserItemClasses.dialogMap.get(tableClass);
        try {
            dialog = dialogClass.getConstructor(tableClass).newInstance(tableClass.getConstructor().newInstance());

        } catch (Exception exc) {
            throw new RuntimeException("Failed to create dialog.", exc);
        }

        dialog.show(null, "Add").ifPresent(a -> table.addData(a));
    }
}
