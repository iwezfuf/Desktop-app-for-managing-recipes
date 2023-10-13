package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class ExportAction extends AbstractAction {

    public ExportAction() {
        super("Export", Icons.DELETE_ICON);
        putValue(SHORT_DESCRIPTION, "Exports data from file");
//        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
