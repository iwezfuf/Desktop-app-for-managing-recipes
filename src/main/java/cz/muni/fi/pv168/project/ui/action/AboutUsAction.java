package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.dialog.AboutUsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AboutUsAction extends AbstractAction {
    private final JFrame parent;

    public AboutUsAction(JFrame parent) {
        super("About Us");
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AboutUsDialog aboutUsDialog = new AboutUsDialog(parent);
        aboutUsDialog.setVisible(true);
    }
}