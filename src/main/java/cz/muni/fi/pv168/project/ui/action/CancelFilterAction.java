package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CancelFilterAction extends AbstractAction {
    public CancelFilterAction() {
        super("Cancel Filter", Icons.CANCEL_FILTER_ICON);
        putValue(SHORT_DESCRIPTION, "Cancels filter");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}