package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.ui.UserInputFields.FormattedInput;

import javax.swing.*;
import java.awt.*;

// TODO redo all dialogs
public class IngredientDialog extends EntityDialog<Ingredient> {

    private final JTextField nameTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();

    private final JTextField nutritionalValueTextField = FormattedInput.createIntTextField(0, 9999);

    private final Ingredient ingredient;

    public IngredientDialog(Ingredient ingredient) {
        super(new Dimension(600, 150));
        this.ingredient = ingredient;

        nameTextField.setColumns(600); // Set the number of visible columns (width).
        limitComponentToOneRow(nameTextField);
        nutritionalValueTextField.setColumns(600);
        limitComponentToOneRow(nutritionalValueTextField);
        setValues();
        addFields();
    }

    private void setValues() {
        nameTextField.setText(ingredient.getName());
        nutritionalValueTextField.setText(ingredient.getNutritionalValue() + "");

        for (Unit unit : Unit.getListOfUnits()) {
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

        return ingredient;
    }
}
