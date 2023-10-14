package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.RecipeCategory;

import javax.swing.*;
import java.awt.*;

public class RecipeCategoryDialog extends EntityDialog<RecipeCategory> {
    private final JTextField nameTextField = new JTextField();
    private final JColorChooser colorChooser = new JColorChooser();

    private final RecipeCategory recipeCategory;

    public RecipeCategoryDialog(RecipeCategory recipeCategory) {
        super(new Dimension(650, 400));
        this.recipeCategory = recipeCategory;

        nameTextField.setColumns(600); // Set the number of visible columns (width).
        limitComponentToOneRow(nameTextField);
        setValues();
        addFields();
    }

    private void setValues() {
        nameTextField.setText(recipeCategory.getName());
        colorChooser.setColor(recipeCategory.getColor());
    }

    private void addFields() {
        add("Category name:", nameTextField);
        addAsScrollable("Category color:", colorChooser);
    }

    @Override
    RecipeCategory getEntity() {

        recipeCategory.setName(nameTextField.getText());
        recipeCategory.setColor(colorChooser.getColor());
        return recipeCategory;
    }
}
