package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marek Eibel
 */
public class RecipeTableComponent extends AbstractTableComponent {

    public RecipeTableComponent(String title, String content) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(title);
        JLabel contentLabel = new JLabel(content);

        add(titleLabel, BorderLayout.NORTH);
        add(contentLabel, BorderLayout.CENTER);

        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional: Add a border
    }

}
