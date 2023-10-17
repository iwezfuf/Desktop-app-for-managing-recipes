package cz.muni.fi.pv168.project.ui.model;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
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

    /**
     * Creates RecipeTableComponent.
     *
     * @param recipe component displays data about the given recipe
     */
    public RecipeTableComponent(Recipe recipe) {

        this.recipe = recipe;

        setLayout(new GridBagLayout());
        setBackground(recipe.getCategory().getColor());
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 15));

        // ------------------------------- //

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        CustomLabel nameLabel = new CustomLabel(recipe.getName());
        nameLabel.makeBold();
        nameLabel.setFontSize(32);

        CustomLabel descriptionLabel = new CustomLabel((recipe.getDescription().isEmpty()) ? "" : " ~ " + recipe.getDescription());
        descriptionLabel.makeItalic();
        descriptionLabel.setFontSize(14);

        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);

        GridBagConstraints textPanelConstraints = new GridBagConstraints();
        textPanelConstraints.fill = GridBagConstraints.BOTH;
        textPanelConstraints.gridy = 0;
        textPanelConstraints.gridx = 0;
        // textPanelConstraints.weightx = 2; TODO what to use agree on later
        textPanelConstraints.weightx = 15;

        // ------------------------------- //

        JPanel portionsPanel = new JPanel();
        portionsPanel.setLayout(new BoxLayout(portionsPanel, BoxLayout.Y_AXIS));

        JLabel portionsIconLabel = new JLabel(Icons.PORTION_ICON);
        String portions = recipe.getNumOfServings() == 1 ? "portion" : "portions";
        CustomLabel portionsLabel = new CustomLabel(recipe.getNumOfServings() + " " + portions);
        portionsPanel.add(portionsIconLabel);
        portionsPanel.add(portionsLabel);

        GridBagConstraints portionsPanelConstraints = new GridBagConstraints();
        portionsPanelConstraints.fill = GridBagConstraints.BOTH;
        portionsPanelConstraints.gridy = 0;
        portionsPanelConstraints.gridx = 1;
        portionsPanelConstraints.weightx = 1;

        // ------------------------------- //

        JPanel nutritionPanel = new JPanel();
        portionsPanel.setLayout(new BoxLayout(portionsPanel, BoxLayout.Y_AXIS));

        JLabel nutritionIconLabel = new JLabel(Icons.NUTRTIONS_ICON);
        CustomLabel nutritionLabel = new CustomLabel(recipe.getNutritionalValue() + " Kcal");
        nutritionPanel.add(nutritionIconLabel);
        nutritionPanel.add(nutritionLabel);

        GridBagConstraints nutritionPanelConstraints = new GridBagConstraints();
        nutritionPanelConstraints.fill = GridBagConstraints.BOTH;
        nutritionPanelConstraints.gridy = 0;
        nutritionPanelConstraints.gridx = 2;
        nutritionPanelConstraints.weightx = 1;

        // ------------------------------- //

        JPanel preparationPanel = new JPanel();
        portionsPanel.setLayout(new BoxLayout(portionsPanel, BoxLayout.Y_AXIS));

        JLabel preparationIconLabel = new JLabel(Icons.TIME_ICON);
        CustomLabel preparationLabel = new CustomLabel(recipe.getPreparationTime() + " minutes");
        preparationPanel.add(preparationIconLabel);
        preparationPanel.add(preparationLabel);

        GridBagConstraints preparationPanelConstrains = new GridBagConstraints();
        preparationPanelConstrains.fill = GridBagConstraints.BOTH;
        preparationPanelConstrains.gridy = 0;
        preparationPanelConstrains.gridx = 3;
        preparationPanelConstrains.weightx = 1;

        // ------------------------------- //

        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CustomLabel categoriesLabel = new CustomLabel(recipe.getCategory().toString());
        categoriesLabel.makeBold();
        categoriesLabel.makeTextLabelUpperCase();

        categoriesPanel.add(categoriesLabel);
        GridBagConstraints categoriesPanelConstraints = new GridBagConstraints();
        categoriesPanelConstraints.fill = GridBagConstraints.BOTH;
        categoriesPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        categoriesPanelConstraints.gridy = 0;
        categoriesPanelConstraints.gridx = 4;
        categoriesPanelConstraints.weightx = 1;

        // ------------------------------- //

        textPanel.setOpaque(false);
        portionsPanel.setOpaque(false);
        nutritionPanel.setOpaque(false);
        preparationPanel.setOpaque(false);
        categoriesPanel.setOpaque(false);

        add(textPanel, textPanelConstraints);
        add(portionsPanel, portionsPanelConstraints);
        add(nutritionPanel, nutritionPanelConstraints);
        add(preparationPanel, preparationPanelConstrains);
        add(categoriesPanel, categoriesPanelConstraints);
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
