package cz.muni.fi.pv168.project.ui.panels.filter;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * Creates new RecipeFilterDialog object.
     */
    public RecipeFilterPanel(EntityTableModelProvider etmp) {
        this.etmp = etmp;

        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bl);

        this.ingredientsSet = new HashSet<>();
        this.ingredientsFilterPanel = new FilterPanel<>(ingredientsSet, etmp.getIngredientTableModel().getEntities(), "ingredient", "ingredients");
        this.add(ingredientsFilterPanel);

        this.recipeCategoriesSet = new HashSet<>();
        this.recipeCategoriesFilterPanel = new FilterPanel<>(recipeCategoriesSet, etmp.getRecipeCategoryTableModel().getEntities(), "recipe category", "recipe categories");
        this.add(recipeCategoriesFilterPanel);

        this.nutritionalRange = new Range(0, 1000000000);
        this.nutritionalValueRangePanel = new RangePanel(nutritionalRange, "Apply filtering by nutritional value", "kcal");
        this.add(nutritionalValueRangePanel);

        this.prepTimeRange = new Range(0, 1000000000);
        this.prepTimeRangePanel = new RangePanel(prepTimeRange, "Apply filtering by preparation time", "min.");
        this.add(prepTimeRangePanel);
    }
}
