package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;

/**
 * Represents RecipeTableComponent.
 * This component serves as a structure to show basic information about the recipe
 * like a name, short description or recipe categories.
 *
 * @author Marek Eibel
 */
public class RecipeTableComponent extends AbstractTableComponent {

    private Recipe recipe;

    /**
     * Creates RecipeTableComponent.
     *
     * @param recipe component displays data about the given recipe
     */
    public RecipeTableComponent(Recipe recipe) {
        this.recipe = recipe;

        initComponents();
        arrangeComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(recipe.getCategory().getColor());
    }

    private void arrangeComponents() {
        add(createTextPanel(), createConstraints(GridBagConstraints.BOTH));
        add(createPanelWithIconAndLabel(Icons.PORTION_ICON, getPortionsText(), 13), createConstraints(GridBagConstraints.BOTH));
        add(createPanelWithIconAndLabel(Icons.NUTRTIONS_ICON, recipe.getNutritionalValue() + " Kcal", 12), createConstraints(GridBagConstraints.BOTH));
        add(createPanelWithIconAndLabel(Icons.TIME_ICON, recipe.getPreparationTime() + " minutes", 15), createConstraints(GridBagConstraints.BOTH));
        add(createCategoriesPanel(), createCategoriesPanelConstraints());
    }

    private JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        CustomLabel nameLabel = new CustomLabel(recipe.getName(), 35);
        nameLabel.makeBold();
        nameLabel.setFontSize(24);

        CustomLabel descriptionLabel = new CustomLabel((recipe.getDescription().isEmpty()) ? "" : " ~ " + recipe.getDescription(), 58);
        descriptionLabel.makeItalic();
        descriptionLabel.setFontSize(14);

        panel.add(nameLabel);
        panel.add(descriptionLabel);

        return panel;
    }

    private JPanel createPanelWithIconAndLabel(Icon icon, String text, int maxCharacters) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        CustomLabel textLabel = new CustomLabel(text, maxCharacters);

        panel.add(iconLabel);
        panel.add(textLabel);

        return panel;
    }

    private GridBagConstraints createConstraints(int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        return constraints;
    }

    private JPanel createCategoriesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);

        CustomLabel categoriesLabel = new CustomLabel(recipe.getCategory().toString(), 15);
        categoriesLabel.makeBold();

        panel.add(categoriesLabel);

        return panel;
    }

    private GridBagConstraints createCategoriesPanelConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        return constraints;
    }

    private String getPortionsText() {
        return recipe.getNumOfServings() + " " + (recipe.getNumOfServings() == 1 ? "portion" : "portions");
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
