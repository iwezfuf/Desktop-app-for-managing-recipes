package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Unit;

import javax.swing.*;
import java.awt.*;

public class UnitDialog extends EntityDialog<Unit> {
    private final JTextField nameTextField = new JTextField();
    private final JComboBox<Unit> unitComboBox = new JComboBox<>();
    private final SpinnerModel ratioModel = new SpinnerNumberModel(1, 1, 100000000, 1);
    private final JSpinner ratioSpinner = new JSpinner(ratioModel);
    private final Unit unit;

    public UnitDialog(Unit unit) {
        super(new Dimension(600, 400));
        this.unit = unit;

        nameTextField.setColumns(600); // Set the number of visible columns (width).
        limitComponentToOneRow(nameTextField);
        setValues();
        addFields();
    }

    private void setValues() {
        for (Unit unit : Unit.listOfUnits) {
            unitComboBox.addItem(unit);
        }
        Unit baseUnit = new Unit("Base", null, -1);
        unitComboBox.addItem(baseUnit);

        nameTextField.setText(unit.getName());
        if (unit.getConversionUnit() != null) {
            unitComboBox.setSelectedItem(unit.getConversionUnit());
        } else {
            unitComboBox.setSelectedItem(baseUnit);
        }
    }

    private void addFields() {
        add("Unit name:", nameTextField);
        add("Conversion unit: ", unitComboBox);
        add("Conversion ratio: ", ratioSpinner);
    }

    @Override
    Unit getEntity() {
        Unit convUnit = (Unit) unitComboBox.getSelectedItem();
        unit.setName(nameTextField.getText());

        unit.setConversionUnit(convUnit);
        unit.setConversionRatio((int) ratioSpinner.getValue());

        return unit;
    }
}
