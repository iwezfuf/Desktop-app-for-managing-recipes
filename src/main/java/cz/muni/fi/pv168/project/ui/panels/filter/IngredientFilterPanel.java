package cz.muni.fi.pv168.project.ui.panels.filter;

import cz.muni.fi.pv168.project.ui.filters.IngredientFilter;
import cz.muni.fi.pv168.project.ui.filters.Range;
import cz.muni.fi.pv168.project.ui.panels.IngredientTablePanel;

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
        //BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        //this.setLayout(bl);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;

        nutritionalRange = new Range(Integer.MIN_VALUE, Integer.MAX_VALUE);
        nutritionalValuePanel = new RangePanel(nutritionalRange, "Nutritional value range:", "kcal");
        this.add(nutritionalValuePanel, gbc);

        JPanel filterBtnPanel = new JPanel();
        BoxLayout bl2 = new BoxLayout(filterBtnPanel, BoxLayout.X_AXIS);
        filterBtnPanel.setLayout(bl2);
        this.filterButton = new JButton("Apply filters");
        filterButton.addActionListener(e -> {
            if (!nutritionalValuePanel.isReadyToFilter()) {
                JOptionPane.showOptionDialog(
                        null,
                        "Cannot filter with invalid filter values.",
                        "Filtering error",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null
                );
                return;
            }

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

        gbc = new GridBagConstraints();
        Insets spacing = new Insets(5, 5, 5, 5);
        gbc.insets = spacing;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(filterBtnPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(Box.createRigidArea(new Dimension(0, 40)), gbc);
    }

    public void clearDialog() {
        this.nutritionalRange.setMin(Integer.MIN_VALUE);
        this.nutritionalRange.setMax(Integer.MAX_VALUE);
        nutritionalValuePanel.clearPanel();
    }
}
