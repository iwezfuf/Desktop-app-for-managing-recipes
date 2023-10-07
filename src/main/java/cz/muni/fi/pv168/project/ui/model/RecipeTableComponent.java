package cz.muni.fi.pv168.project.ui.model;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents RecipeTableComponent.
 * This component servers as a structure to show basic information about the recipe
 * like a name, short description or recipe categories.
 *
 * @author Marek Eibel
 */
public class RecipeTableComponent extends AbstractTableComponent {

    public RecipeTableComponent(String title, String content) { // TODO: add Recipe recipe as an argument here

        setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.orange);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel titleLabel = new JLabel(title);
        JLabel textLabel = new JLabel(content);
        textPanel.add(titleLabel);
        textPanel.add(textLabel);

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel iconLabel = new JLabel(Icons.ADD_ICON);

        iconPanel.add(iconLabel);

        add(textPanel, BorderLayout.CENTER);
        add(iconPanel, BorderLayout.EAST);
    }
}
