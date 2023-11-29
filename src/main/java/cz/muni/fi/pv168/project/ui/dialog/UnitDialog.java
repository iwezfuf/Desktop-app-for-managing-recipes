package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.model.FormattedInput;

import javax.swing.*;
import java.util.Objects;

public class UnitDialog extends EntityDialog<Unit> {
    private final JTextField nameTextField = new JTextField();
    private final JTextField abbreviationTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();
    private final JTextField ratioTextField = FormattedInput.createFloatTextField(0, 10000);
    private final Unit unit;

    public UnitDialog(Unit unit,
                      EntityTableModelProvider entityTableModelProvider,
                      Validator<Unit> entityValidator) {
        super(entityTableModelProvider, Objects.requireNonNull(entityValidator));
        this.unit = unit;

        unitComboBox.addActionListener(e ->
                ratioTextField.setEnabled(!Objects.equals(((Unit) unitComboBox.getSelectedItem()).getName(), "Base unit")));

        setValues();
        addFields();
    }



    private void setValues() {
        for (Unit unit : entityTableModelProvider.getUnitTableModel().getEntities()) {
            unitComboBox.addItem(unit);
        }

        Unit baseUnit = new Unit("Base unit", null, -1, "");
        unitComboBox.addItem(baseUnit);

        nameTextField.setText(unit.getName());
        abbreviationTextField.setText(unit.getAbbreviation());
        if (!unit.isBaseUnit()) {
            unitComboBox.setSelectedItem(unit.getConversionUnit());
        } else {
            unitComboBox.setSelectedItem(baseUnit);
        }
        ratioTextField.setText(unit.getConversionRatio() + "");
    }

    private void addFields() {
        add("Unit name:", nameTextField);
        add("Abbreviation:", abbreviationTextField);
        add("Conversion unit: ", unitComboBox);
        add("Conversion ratio: ", ratioTextField);
    }

    private boolean checkConversionRatio() {

        try {
            unit.setConversionRatio(FormattedInput.getFloat(ratioTextField.getText()));
        } catch (NumberFormatException e) {
            new JOptionPane().showMessageDialog(null, "Invalid conversion ratio value.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @Override
    Unit getEntity() {

        Unit convUnit = (Unit) unitComboBox.getSelectedItem();
        unit.setName(nameTextField.getText());
        unit.setAbbreviation(abbreviationTextField.getText());

        assert convUnit != null;
        unit.setConversionUnit(!convUnit.getName().equals("Base unit") ? convUnit : null);
        if (!checkConversionRatio()) {
            return null;
        }

        ValidationResult result = entityValidator.validate(unit);
        if (!result.isValid()) {
            new JOptionPane().showMessageDialog(null, "Invalid entered data: " + result.getValidationErrors() + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return unit;
    }

    @Override
    public void configureReadOnlyMode() {
        nameTextField.setEditable(false);
        abbreviationTextField.setEditable(false);
        unitComboBox.setEnabled(false);
        ratioTextField.setEditable(false);
    }

}
