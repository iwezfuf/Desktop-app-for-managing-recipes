package cz.muni.fi.pv168.project.ui.panels.filter;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.filters.Range;
import cz.muni.fi.pv168.project.ui.filters.RecipeFilter;
import cz.muni.fi.pv168.project.ui.panels.RecipeTablePanel;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a panel for configuring and applying filters to recipes.
 * <p>
 * This panel allows users to specify filter criteria such as ingredients, recipe categories,
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
    private JButton resetFilterButton;

    /**
     * Creates new RecipeFilterDialog object.
     */
    public RecipeFilterPanel(RecipeTablePanel recipeTablePanel) {
        this.etmp = recipeTablePanel.getEtmp();

        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bl);

        this.ingredientsSet = new HashSet<>();
        this.ingredientsFilterPanel = new FilterPanel<>(ingredientsSet, etmp.getIngredientTableModel().getEntities(), "ingredient", "ingredients");
        ingredientsFilterPanel.getComboBox().addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                ingredientsFilterPanel.fillComboBox(etmp.getIngredientTableModel().getEntities());
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        this.add(ingredientsFilterPanel);

        this.recipeCategoriesSet = new HashSet<>();
        this.recipeCategoriesFilterPanel = new FilterPanel<>(recipeCategoriesSet, etmp.getRecipeCategoryTableModel().getEntities(), "recipe category", "recipe categories");
        recipeCategoriesFilterPanel.getComboBox().addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                recipeCategoriesFilterPanel.fillComboBox(etmp.getRecipeCategoryTableModel().getEntities());
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        this.add(recipeCategoriesFilterPanel);

        this.nutritionalRange = new Range(0, 1000000000);
        this.nutritionalValueRangePanel = new RangePanel(nutritionalRange, "Nutritional value:", "kcal");
        this.add(nutritionalValueRangePanel);

        this.prepTimeRange = new Range(0, 1000000000);
        this.prepTimeRangePanel = new RangePanel(prepTimeRange, "Preparation time:", "min.");
        this.add(prepTimeRangePanel);

        JPanel filterBtnPanel = new JPanel();
        BoxLayout bl2 = new BoxLayout(filterBtnPanel, BoxLayout.X_AXIS);
        filterBtnPanel.setLayout(bl2);
        this.filterButton = new JButton("Apply filters");
        filterButton.addActionListener(e -> {
            recipeTablePanel.applyFilter(new RecipeFilter(ingredientsSet, recipeCategoriesSet, nutritionalRange, prepTimeRange));
            recipeTablePanel.refresh();
            recipeTablePanel.revalidate();
            recipeTablePanel.repaint();
            recipeTablePanel.getTable().revalidate();
            recipeTablePanel.getTable().repaint();
        });
        filterBtnPanel.add(filterButton);

        filterBtnPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        this.resetFilterButton = new JButton("Reset filters");
        resetFilterButton.addActionListener(e -> {
            recipeTablePanel.cancelFilter();
            this.clearDialog();
            recipeTablePanel.refresh();
            recipeTablePanel.revalidate();
            recipeTablePanel.repaint();
            recipeTablePanel.getTable().revalidate();
            recipeTablePanel.getTable().repaint();
        });
        filterBtnPanel.add(resetFilterButton);
        this.add(filterBtnPanel);

        this.add(Box.createRigidArea(new Dimension(0, 40)));
    }

    private void clearDialog() {
        ingredientsSet.clear();
        recipeCategoriesSet.clear();
        this.nutritionalRange = new Range(0, 1000000000);
        this.prepTimeRange = new Range(0, 1000000000);
        this.ingredientsFilterPanel.clearPanel();
        this.recipeCategoriesFilterPanel.clearPanel();
        this.nutritionalValueRangePanel.clearPanel();
        this.prepTimeRangePanel.clearPanel();
    }
}
