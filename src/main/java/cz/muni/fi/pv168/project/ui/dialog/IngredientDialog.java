package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;

import javax.swing.*;
import java.util.Objects;

/**
 * @author Marek Eibel
 */
public class IngredientDialog extends EntityDialog<Ingredient> {

    private final Ingredient ingredient;

    private final JTextField nameTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();
    private final JTextField nutritionalValueTextField = new JTextField();

    public IngredientDialog(Ingredient ingredient, EntityTableModelProvider entityTableModelProvider,
                            Validator<Ingredient> entityValidator) {

        super(ingredient, entityTableModelProvider, Objects.requireNonNull(entityValidator));

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

        for (Unit unit : entityTableModelProvider.getUnitTableModel().getEntities()) {
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
        ingredient.setNutritionalValue(Integer.parseInt(nutritionalValueTextField.getText()));
        ingredient.setUnit((Unit) unitComboBox.getSelectedItem());

        return ingredient;
    }
}
