package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;

import javax.swing.*;
import java.util.Objects;

public class RecipeCategoryDialog extends EntityDialog<RecipeCategory> {
    private final JTextField nameTextField = new JTextField();
    private final JColorChooser colorChooser = new JColorChooser();

    private final RecipeCategory recipeCategory;

    public RecipeCategoryDialog(RecipeCategory recipeCategory,
                                EntityTableModelProvider entityTableModelProvider,
                                Validator<RecipeCategory> entityValidator) {
        super(recipeCategory, entityTableModelProvider, Objects.requireNonNull(entityValidator));
        this.recipeCategory = recipeCategory;
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

        ValidationResult result = entityValidator.validate(recipeCategory);
        if (!result.isValid()) {
            new JOptionPane().showMessageDialog(null, "Invalid entered data: " + result.getValidationErrors() + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return recipeCategory;
    }
}
