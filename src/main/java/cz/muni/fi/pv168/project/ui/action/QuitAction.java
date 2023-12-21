package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class QuitAction extends AbstractAction {

    private JFrame frame;

    public QuitAction(JFrame frame) {
        super("Quit", Icons.QUIT_ICON);
        putValue(SHORT_DESCRIPTION, "Terminates the application");
        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int option = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to quit?",
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            if (frame != null) {
                frame.dispose();
            } else {
                System.exit(1);
            }
        }
    }
}
