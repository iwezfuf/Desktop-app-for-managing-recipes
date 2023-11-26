package cz.muni.fi.pv168.project.ui.panels.filter;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.filters.RecipeFilter;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;
import cz.muni.fi.pv168.project.ui.filters.Range;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import java.awt.Component;
import java.awt.Container;

/**
 * Represents a dialog for configuring and applying filters to recipes.
 *
 * This dialog allows users to specify filter criteria such as ingredients, recipe categories,
 * and desired calorie ranges.
 *
 * @author Marek Eibel
 */
public class RecipeFilterPanel extends JPanel {

    private EntityTableModelProvider etmp;
    private Set<Ingredient> ingredientsSet;
    private FilterPanel<Ingredient> ingredientsFilterPanel;
    private Set<RecipeCategory> recipeCategoriesSet;
    private FilterPanel<RecipeCategory> recipeCategoriesFilterPanel;
    private Range nutritionalRange;
    private RangePanel nutritionalValueRangePanel;
    private Range prepTimeRange;
    private RangePanel prepTimeRangePanel;

    private JButton filterButton;

    /**
     * Creates new RecipeFilterDialog object.
     */
    public RecipeFilterPanel(RecipeTablePanel recipeTablePanel) {
        this.etmp = recipeTablePanel.getEtmp();

        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bl);

        this.ingredientsSet = new HashSet<>();
        this.ingredientsFilterPanel = new FilterPanel<>(ingredientsSet, etmp.getIngredientTableModel().getEntities(), "ingredient", "ingredients");
        JButton arrowBtn = getButtonSubComponent(ingredientsFilterPanel.getComboBox());
        arrowBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingredientsFilterPanel.fillComboBox(etmp.getIngredientTableModel().getEntities());
            }
        });
        this.add(ingredientsFilterPanel);

        this.recipeCategoriesSet = new HashSet<>();
        this.recipeCategoriesFilterPanel = new FilterPanel<>(recipeCategoriesSet, etmp.getRecipeCategoryTableModel().getEntities(), "recipe category", "recipe categories");
        JButton arrowBtn2 = getButtonSubComponent(recipeCategoriesFilterPanel.getComboBox());
        arrowBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipeCategoriesFilterPanel.fillComboBox(etmp.getRecipeCategoryTableModel().getEntities());
            }
        });
        this.add(recipeCategoriesFilterPanel);

        this.nutritionalRange = new Range(0, 1000000000);
        this.nutritionalValueRangePanel = new RangePanel(nutritionalRange, "Apply filtering by nutritional value", "kcal");
        this.add(nutritionalValueRangePanel);

        this.prepTimeRange = new Range(0, 1000000000);
        this.prepTimeRangePanel = new RangePanel(prepTimeRange, "Apply filtering by preparation time", "min.");
        this.add(prepTimeRangePanel);

        this.filterButton = new JButton("Apply filters");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipeTablePanel.applyFilter(new RecipeFilter(ingredientsSet, recipeCategoriesSet, nutritionalRange, prepTimeRange));
                recipeTablePanel.refresh();
                recipeTablePanel.revalidate();
                recipeTablePanel.repaint();
                recipeTablePanel.getTable().revalidate();
                recipeTablePanel.getTable().repaint();
            }
        });
        this.add(filterButton);
    }

    private JButton getButtonSubComponent(Container container) {
        if (container instanceof JButton) {
            return (JButton) container;
        } else {
            Component[] components = container.getComponents();
            for (Component component : components) {
                if (component instanceof Container) {
                    return getButtonSubComponent((Container)component);
                }
            }
        }
        return null;
    }
}
