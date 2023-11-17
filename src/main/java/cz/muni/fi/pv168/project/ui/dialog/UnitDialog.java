package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;

import javax.swing.*;
import java.util.Objects;

public class UnitDialog extends EntityDialog<Unit> {
    private final JTextField nameTextField = new JTextField();
    private final JTextField abbreviationTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();
    private final JTextField ratioTextField = new JTextField();
    private final Unit unit;

    public UnitDialog(Unit unit,
                      EntityTableModelProvider entityTableModelProvider,
                      Validator<Unit> entityValidator) {
        super(unit, entityTableModelProvider, Objects.requireNonNull(entityValidator));
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

    @Override
    Unit getEntity() {
        Unit convUnit = (Unit) unitComboBox.getSelectedItem();
        unit.setName(nameTextField.getText());
        unit.setAbbreviation(abbreviationTextField.getText());

        assert convUnit != null;
        unit.setConversionUnit(!convUnit.getName().equals("Base unit") ? convUnit : null);
        unit.setConversionRatio(Float.parseFloat(ratioTextField.getText()));

        return unit;
    }
}
