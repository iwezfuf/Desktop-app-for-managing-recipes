package cz.muni.fi.pv168.project.ui.panels.filter;

import cz.muni.fi.pv168.project.ui.filters.IngredientFilter;
import cz.muni.fi.pv168.project.ui.filters.Range;
import cz.muni.fi.pv168.project.ui.filters.RecipeFilter;
import cz.muni.fi.pv168.project.ui.panels.IngredientTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngredientFilterPanel extends JPanel {
    private final Range nutritionalRange;
    private final RangePanel nutritionalValuePanel;
    private JButton filterButton;

    public IngredientFilterPanel(IngredientTablePanel ingredientTablePanel) {
        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bl);

        nutritionalRange = new Range(0, 1000000000);
        nutritionalValuePanel = new RangePanel(nutritionalRange, "Filter ingredients by their nutritional value", "kcal");
        this.add(nutritionalValuePanel);

        this.filterButton = new JButton("Apply filters");
        filterButton.addActionListener(e -> {
            ingredientTablePanel.applyFilter(new IngredientFilter(nutritionalRange));
            ingredientTablePanel.getTable().revalidate();
            ingredientTablePanel.getTable().repaint();
        });
        this.add(filterButton);
    }
}
