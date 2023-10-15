package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Unit;

import javax.swing.*;
import java.awt.*;

// TODO better design
public class UnitTableComponent extends AbstractTableComponent {
    private Unit unit;

    public UnitTableComponent(Unit unit) {
        this.unit = unit;

        setBackground(Color.orange);
        //var bl = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel(unit.getName());
        add(nameLabel);
        JLabel conversionUnitLabel = new JLabel(unit.getConversionUnit() != null ? unit.getConversionUnit().getName() : "Base unit");
        add(conversionUnitLabel);

        if (unit.getConversionUnit() != null) {
            JLabel ratioLabel = new JLabel("1 " + unit.getName() + " = " + unit.getConversionUnit().getRatio());
            add(ratioLabel);
        }
    }
}
