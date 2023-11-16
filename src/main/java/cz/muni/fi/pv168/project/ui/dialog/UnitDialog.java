package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.ui.UserInputFields.FloatTextField;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UnitDialog extends EntityDialog<Unit> {
    private final JTextField nameTextField = new JTextField();
    private final JTextField abbreviationTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();
    private final JTextField ratioTextField = new FloatTextField();
    private final Unit unit;

    public UnitDialog(Unit unit) {
        super(new Dimension(600, 200));
        this.unit = unit;

        nameTextField.setColumns(600); // Set the number of visible columns (width).
        limitComponentToOneRow(nameTextField);
        abbreviationTextField.setColumns(600);
        limitComponentToOneRow(abbreviationTextField);

        unitComboBox.addActionListener(e ->
                ratioTextField.setEnabled(!Objects.equals(((Unit) unitComboBox.getSelectedItem()).getName(), "Base unit")));

        setValues();
        addFields();
    }

    private void setValues() {
        for (Unit unit : Unit.getListOfUnits()) {
            unitComboBox.addItem(unit);
        }

        Unit baseUnit = new Unit("Base unit", null, -1, "");
        Unit.getListOfUnits().remove(baseUnit);
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

        unit.setConversionUnit(!convUnit.getName().equals("Base unit") ? convUnit : null);
        unit.setConversionRatio(Float.parseFloat(ratioTextField.getText()));

        return unit;
    }
}
