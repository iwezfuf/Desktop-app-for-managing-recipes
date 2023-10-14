package cz.muni.fi.pv168.project.ui.model;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class IngredientTableComponent extends AbstractTableComponent {

    private Ingredient ingredient;

    public IngredientTableComponent(Ingredient ingredient) {

        this.ingredient = ingredient;

        setLayout(new GridBagLayout());
        setBackground(Color.orange);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.orange);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel(ingredient.getName());
        textPanel.add(nameLabel);
        GridBagConstraints textPanelConstraints = new GridBagConstraints();
        textPanelConstraints.fill = GridBagConstraints.BOTH;
        textPanelConstraints.gridy = 0;
        textPanelConstraints.gridx = 0;
        textPanelConstraints.weightx = 4;
        textPanel.setOpaque(false);
        add(textPanel, textPanelConstraints);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
