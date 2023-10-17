package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import javax.swing.*;
import java.awt.*;

public class IngredientDialog extends EntityDialog<Ingredient> {

    private final JTextField nameTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();

    private final JSlider nutritionalValueSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 100);

    private final Ingredient ingredient;

    public IngredientDialog(Ingredient ingredient) {
        super(new Dimension(600, 150));
        this.ingredient = ingredient;

        nameTextField.setColumns(600); // Set the number of visible columns (width).
        limitComponentToOneRow(nameTextField);
        this.nutritionalValueSlider.setMajorTickSpacing(50);
        this.nutritionalValueSlider.setMinorTickSpacing(10);
        this.nutritionalValueSlider.setPaintTicks(true);
        this.nutritionalValueSlider.setPaintLabels(true);
        setValues();
        addFields();
    }

    private void setValues() {
        nameTextField.setText(ingredient.getName());
        nutritionalValueSlider.setValue(ingredient.getNutritionalValue());

        for (Unit unit : Unit.getListOfUnits()) {
            unitComboBox.addItem(unit);
        }

        if (ingredient.getUnit() != null) {
            unitComboBox.setSelectedItem(ingredient.getUnit());
        }
    }

    private void addFields() {
        add("Ingredient name:", nameTextField);
        addAsScrollable("Nutritional value: [kcal]", nutritionalValueSlider);
        add("Unit:", unitComboBox);
    }

    @Override
    Ingredient getEntity() {

        ingredient.setName(nameTextField.getText());
        ingredient.setNutritionalValue(nutritionalValueSlider.getValue());
        ingredient.setUnit((Unit) unitComboBox.getSelectedItem());

        return ingredient;
    }
}
