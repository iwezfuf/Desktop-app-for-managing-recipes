package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Range;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeFilter;
import cz.muni.fi.pv168.project.ui.model.Tab;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import cz.muni.fi.pv168.project.model.AbstractFilter;

/**
 * Represents a dialog for configuring and applying filters to recipes.
 *
 * This dialog allows users to specify filter criteria such as ingredients, recipe categories,
 * and desired calorie ranges.
 *
 * @author Marek Eibel
 */
public class RecipeFilterDialog extends EntityDialog<RecipeFilter> {

    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final Set<Ingredient> ingredientsSet = new HashSet<>();
    private final FilterPanel<Ingredient> ingredientsTabPanel = new FilterPanel<>(ingredientsSet, Ingredient.getListOfIngredients(), "ingredient", "ingredients");
    private final Set<RecipeCategory> recipeCategoriesSet = new HashSet<>();
    private final FilterPanel<RecipeCategory> recipeCategoriesTabPanel = new FilterPanel<>(recipeCategoriesSet, RecipeCategory.getListOfCategories(), "recipe category", "recipe categories");
    private final Range nutritionalRange = new Range(0, 1000000000);
    private final RangePanel nutritionalValueTabPanel = new RangePanel(nutritionalRange, "Apply filtering by nutritional value", "kcal");
    private final Range prepTimeRange = new Range(0, 1000000000);
    private final RangePanel prepTimeTabPanel = new RangePanel(prepTimeRange, "Apply filtering by preparation time", "min.");

    /**
     * Creates new RecipeFilterDialog object.
     */
    public RecipeFilterDialog(AbstractFilter filter) {
        super(new Dimension(800, 600));
        this.setLayout(new BorderLayout());

        fillFilter(filter);
        createTabbedPane(getTabs());
    }

    private void fillFilter(AbstractFilter filter) {
        if (filter == null) {
            return;
        }
        RecipeFilter rf = (RecipeFilter) filter;
        ingredientsSet.addAll(rf.getIngredientsInFilter());
        ingredientsTabPanel.fillFilterPanel(ingredientsSet);
        recipeCategoriesSet.addAll(rf.getRecipeCategoriesInFilter());
        recipeCategoriesTabPanel.fillFilterPanel(recipeCategoriesSet);
        nutritionalRange.setMin(rf.getNutritionValueRange().getMin());
        nutritionalRange.setMax(rf.getNutritionValueRange().getMax());
        nutritionalValueTabPanel.fillRange(nutritionalRange);
        prepTimeRange.setMin(rf.getPreperationTimeRange().getMin());
        prepTimeRange.setMax(rf.getPreperationTimeRange().getMax());
        prepTimeTabPanel.fillRange(prepTimeRange);
    }

    private List<Tab> getTabs() {
        List<Tab> tabs = List.of(
                new Tab(ingredientsTabPanel, "Ingredients", Icons.BOOK_ICON, "Ingredients filter"),
                new Tab(recipeCategoriesTabPanel, "Recipe categories", Icons.INGREDIENTS_ICON, "Recipe categories filter"),
                new Tab(nutritionalValueTabPanel, "Nutritional value", Icons.WEIGHTS_ICON, "Nutritional value filter"),
                new Tab(prepTimeTabPanel, "Preparation time", Icons.CATEGORY_ICON, "Preparation time filter")
        );

        return tabs;
    }

    private void createTabbedPane(List<Tab> tabbedPanesList) {
        this.add(tabbedPane, BorderLayout.CENTER);
        for (Tab tab : tabbedPanesList) {
            tabbedPane.addTab(tab.getName(), tab.getIcon(), tab.getComponent(), tab.getTooltip());
        }
    }

    /**
     * Gets the RecipeFilter generated by the user input inside a dialog window.
     *
     * @return A RecipeFilter with values for ingredients, categories, and calorie ranges.
     */
    @Override
    RecipeFilter getEntity() {
        return new RecipeFilter(ingredientsSet, recipeCategoriesSet, nutritionalRange, prepTimeRange);
    }
}
