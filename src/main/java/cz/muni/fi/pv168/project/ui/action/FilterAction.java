package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FilterAction extends AbstractAction {
    public FilterAction() {
        super("Filter", Icons.FILTER_ICON);
        putValue(SHORT_DESCRIPTION, "Filters data");
        putValue(MNEMONIC_KEY, KeyEvent.VK_F);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
