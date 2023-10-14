package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.RecipeCategory;

import javax.swing.*;
import javax.swing.border.Border;

public class RecipeCategoryTableComponent extends AbstractTableComponent {
    private RecipeCategory recipeCategory;

    public RecipeCategoryTableComponent(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;

//        Border border = BorderFactory.createLineBorder(recipeCategory.getColor(), 1);
//        setBorder(border);
        setBackground(recipeCategory.getColor());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel(recipeCategory.getName());
        add(nameLabel);
    }
}
