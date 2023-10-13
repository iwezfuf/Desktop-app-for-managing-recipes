package cz.muni.fi.pv168.project.ui.model;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Marek Eibel
 */
public class CustomMouseListener implements MouseListener {

    private final ActionEvent mouseClickedActionEvent;

    CustomMouseListener(ActionEvent event) {
        mouseClickedActionEvent = event;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
