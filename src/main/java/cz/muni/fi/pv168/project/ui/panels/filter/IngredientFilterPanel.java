package cz.muni.fi.pv168.project.ui.panels.filter;

import cz.muni.fi.pv168.project.ui.filters.IngredientFilter;
import cz.muni.fi.pv168.project.ui.filters.Range;
import cz.muni.fi.pv168.project.ui.filters.RecipeFilter;
import cz.muni.fi.pv168.project.ui.panels.IngredientTablePanel;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngredientFilterPanel extends JPanel {
    private Range nutritionalRange;
    private final RangePanel nutritionalValuePanel;
    private JButton filterButton;
    private JButton resetFilterButton;

    public IngredientFilterPanel(IngredientTablePanel ingredientTablePanel) {
        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bl);

        nutritionalRange = new Range(0, 1000000000);
        nutritionalValuePanel = new RangePanel(nutritionalRange, "Filter ingredients by their nutritional value", "kcal");
        this.add(nutritionalValuePanel);

        JPanel filterBtnPanel = new JPanel();
        BoxLayout bl2 = new BoxLayout(filterBtnPanel, BoxLayout.X_AXIS);
        filterBtnPanel.setLayout(bl2);
        this.filterButton = new JButton("Apply filters");
        filterButton.addActionListener(e -> {
            ingredientTablePanel.applyFilter(new IngredientFilter(nutritionalRange));
            ingredientTablePanel.getTable().revalidate();
            ingredientTablePanel.getTable().repaint();
        });
        filterBtnPanel.add(filterButton);

        filterBtnPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        this.resetFilterButton = new JButton("Reset filters");
        resetFilterButton.addActionListener(e -> {
            ingredientTablePanel.cancelFilter();
            this.clearDialog();
            ingredientTablePanel.refresh();
            ingredientTablePanel.revalidate();
            ingredientTablePanel.repaint();
            ingredientTablePanel.getTable().revalidate();
            ingredientTablePanel.getTable().repaint();
        });
        filterBtnPanel.add(resetFilterButton);
        this.add(filterBtnPanel);

        this.add(Box.createRigidArea(new Dimension(0, 40)));
    }

    public void clearDialog() {
        this.nutritionalRange = new Range(0, 1000000000);
        nutritionalValuePanel.clearPanel();
    }
}
