package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;

import javax.swing.*;
import java.awt.*;

public class RecipeCategoryTableComponent extends AbstractTableComponent {
    private RecipeCategory recipeCategory;

    public RecipeCategoryTableComponent(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 15));
        setBackground(Color.getHSBColor(0.2f, 0.4f, 0.9f));

        setBackground(recipeCategory.getColor());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        CustomLabel nameLabel = new CustomLabel(recipeCategory.getName(), 40);
        nameLabel.makeBold();
        nameLabel.setFontSize(24);

        add(nameLabel);
    }
}
