package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;
import cz.muni.fi.pv168.project.ui.model.FormattedInput;

import javax.swing.*;
import java.util.Objects;

/**
 * @author Marek Eibel
 */
public class IngredientDialog extends EntityDialog<Ingredient> {

    private final Ingredient ingredient;

    private final JTextField nameTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();
    private final JTextField nutritionalValueTextField = FormattedInput.createIntTextField(0, 9999);

    public IngredientDialog(Ingredient ingredient, EntityTableModelProviderWithCrud entityTableModelProviderWithCrud,
                            Validator<Ingredient> entityValidator) {

        super(entityTableModelProviderWithCrud, Objects.requireNonNull(entityValidator));

        this.ingredient = ingredient;
        initField();
        setValues();
        addFields();
    }

    private void initField() {

        final int width = 35;

        nameTextField.setColumns(width);
        nutritionalValueTextField.setColumns(width);
    }

    private void setValues() {

        nameTextField.setText(ingredient.getName());
        nutritionalValueTextField.setText(ingredient.getNutritionalValue() + "");

        for (Unit unit : entityTableModelProviderWithCrud.getUnitTableModel().getEntities()) {
            unitComboBox.addItem(unit);
        }

        if (ingredient.getUnit() != null) {
            unitComboBox.setSelectedItem(ingredient.getUnit());
        }
    }

    private void addFields() {
        add("Ingredient name:", nameTextField);
        add("Nutritional value: [kcal]", nutritionalValueTextField);
        add("Unit:", unitComboBox);
    }

    @Override
    Ingredient getEntity() {

        ingredient.setName(nameTextField.getText());
        ingredient.setNutritionalValue(FormattedInput.getInt(nutritionalValueTextField.getText()));
        ingredient.setUnit((Unit) unitComboBox.getSelectedItem());

        ValidationResult result = entityValidator.validate(ingredient);
        if (!result.isValid()) {
            new JOptionPane().showMessageDialog(null, "Invalid entered data: " + result.getValidationErrors() + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return ingredient;
    }

    public void addViewFields() {
        JLabel ingredientNameLabel = new JLabel(ingredient.getName());
        add("Ingredient name:", ingredientNameLabel);

        JLabel nutritionalValueLabel = new JLabel(String.valueOf(ingredient.getNutritionalValue()));
        add("Nutritional value: [kcal]", nutritionalValueLabel);

        JLabel unitLabel = new JLabel(ingredient.getUnit() != null ? ingredient.getUnit().getName() : "None");
        add("Unit:", unitLabel);
    }

    @Override
    public void configureReadOnlyMode() {
        getPanel().removeAll();
        addViewFields();
    }

}
