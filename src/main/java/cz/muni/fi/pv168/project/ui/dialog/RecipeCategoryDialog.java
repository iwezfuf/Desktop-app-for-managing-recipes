package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RecipeCategoryDialog extends EntityDialog<RecipeCategory> {
    private final JTextField nameTextField = new JTextField();
    private final JColorChooser colorChooser = new JColorChooser();

    private final RecipeCategory recipeCategory;

    public RecipeCategoryDialog(RecipeCategory recipeCategory,
                                EntityTableModelProviderWithCrud entityTableModelProviderWithCrud,
                                Validator<RecipeCategory> entityValidator) {
        super(entityTableModelProviderWithCrud, Objects.requireNonNull(entityValidator));
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
            new InvalidDataDialog(result.getValidationErrors());
            return null;
        }

        return recipeCategory;
    }

    public void addViewFields() {
        JLabel categoryNameLabel = new JLabel(recipeCategory.getName());
        add("Category name:", categoryNameLabel);

        JLabel colorLabel = new JLabel();
        colorLabel.setOpaque(true);
        colorLabel.setBackground(recipeCategory.getColor());
        colorLabel.setPreferredSize(new Dimension(25, 25));
        addAsScrollable("Category color:", colorLabel);
    }

    @Override
    public void configureReadOnlyMode() {
        getPanel().removeAll();
        addViewFields();
    }
}
