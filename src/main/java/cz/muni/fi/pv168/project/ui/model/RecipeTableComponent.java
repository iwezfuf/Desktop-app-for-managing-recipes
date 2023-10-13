package cz.muni.fi.pv168.project.ui.model;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents RecipeTableComponent.
 * This component servers as a structure to show basic information about the recipe
 * like a name, short description or recipe categories.
 *
 * @author Marek Eibel
 */
public class RecipeTableComponent extends AbstractTableComponent {

    private Recipe recipe;

    public RecipeTableComponent(Recipe recipe) {

        this.recipe = recipe;

        setLayout(new GridBagLayout());

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.orange);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        //textPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel nameLabel = new JLabel(recipe.getName());
        JLabel descriptionLabel = new JLabel("Portions: " + recipe.getNumOfServings());
        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);
        GridBagConstraints textPanelConstraints = new GridBagConstraints();
        textPanelConstraints.fill = GridBagConstraints.BOTH;
        textPanelConstraints.gridy = 0;
        textPanelConstraints.gridx = 0;
        textPanelConstraints.weightx = 4;

        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        JLabel ingredientsLabel = new JLabel("Ingredients:\n" + recipe.getIngredients());
        JLabel nutritionLabel = new JLabel("Nutritional value: " + recipe.getNutritionalValue());
        ingredientsPanel.add(ingredientsLabel);
        ingredientsPanel.add(nutritionLabel);
        GridBagConstraints ingredientsPanelConstraints = new GridBagConstraints();
        ingredientsPanelConstraints.fill = GridBagConstraints.BOTH;
        ingredientsPanelConstraints.gridy = 0;
        ingredientsPanelConstraints.gridx = 1;
        ingredientsPanelConstraints.weightx = 1;

        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));
        JLabel iconLabel = new JLabel(Icons.TIME_ICON);
        JLabel preparationTimeLabel = new JLabel(String.valueOf(recipe.getPreparationTime()));
        iconPanel.add(iconLabel);
        iconPanel.add(preparationTimeLabel);
        GridBagConstraints iconPanelConstraints = new GridBagConstraints();
        iconPanelConstraints.fill = GridBagConstraints.BOTH;
        iconPanelConstraints.gridy = 0;
        iconPanelConstraints.gridx = 2;
        iconPanelConstraints.weightx = 1;

        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel categoriesLabel = new JLabel("Categories: " + recipe.getCategory());
        categoriesPanel.add(categoriesLabel);
        GridBagConstraints categoriesPanelConstraints = new GridBagConstraints();
        categoriesPanelConstraints.fill = GridBagConstraints.BOTH;
        categoriesPanelConstraints.gridy = 0;
        categoriesPanelConstraints.gridx = 3;
        categoriesPanelConstraints.weightx = 1;

        add(textPanel, textPanelConstraints);
        add(ingredientsPanel, ingredientsPanelConstraints);
        add(iconPanel, iconPanelConstraints);
        add(categoriesPanel, categoriesPanelConstraints);
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
