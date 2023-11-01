package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;

import javax.swing.*;
import java.awt.*;

public class IngredientTableComponent extends AbstractTableComponent {

    private Ingredient ingredient;

    public IngredientTableComponent(Ingredient ingredient) {
        this.ingredient = ingredient;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 15));
        setBackground(Color.orange);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.orange);
        textPanel.setLayout(new GridBagLayout());

        CustomLabel nameLabel = new CustomLabel(ingredient.getName(), 37);
        nameLabel.makeBold();
        nameLabel.setFontSize(24);

        CustomLabel descriptionLabel = new CustomLabel(ingredient.getNutritionalValue() + " kcal / " + ingredient.getUnit().getName(), 70);
        descriptionLabel.makeItalic();
        descriptionLabel.setFontSize(14);

        CustomLabel statisticsLabel = new CustomLabel("Used in " + ingredient.getRecipeCount(Recipe.getListOfRecipes()) +
                " (" + ingredient.getRecipeCountPercentage(Recipe.getListOfRecipes()) + "%)" + " recipes", 40);
        statisticsLabel.makeItalic();
        statisticsLabel.setFontSize(16);

        GridBagConstraints nameLabelConstraints = new GridBagConstraints();
        nameLabelConstraints.gridx = 0;
        nameLabelConstraints.gridy = 0;
        nameLabelConstraints.anchor = GridBagConstraints.WEST;
        textPanel.add(nameLabel, nameLabelConstraints);

        GridBagConstraints descriptionLabelConstraints = new GridBagConstraints();
        descriptionLabelConstraints.gridx = 0;
        descriptionLabelConstraints.gridy = 1;
        descriptionLabelConstraints.anchor = GridBagConstraints.WEST;
        textPanel.add(descriptionLabel, descriptionLabelConstraints);

        GridBagConstraints statisticsLabelConstraints = new GridBagConstraints();
        statisticsLabelConstraints.gridx = 1;
        statisticsLabelConstraints.gridy = 0;
        statisticsLabelConstraints.anchor = GridBagConstraints.EAST;
        statisticsLabelConstraints.weightx = 1.0;
        textPanel.add(statisticsLabel, statisticsLabelConstraints);

        GridBagConstraints textPanelConstraints = new GridBagConstraints();
        textPanelConstraints.fill = GridBagConstraints.BOTH;
        textPanelConstraints.gridy = 0;
        textPanelConstraints.gridx = 0;
        textPanelConstraints.weightx = 1.0;
        textPanelConstraints.insets = new Insets(0, 0, 0, 0);
        textPanel.setOpaque(false);
        add(textPanel, textPanelConstraints);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
