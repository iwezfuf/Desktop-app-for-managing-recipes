package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.dialog.UnitDialog;
import cz.muni.fi.pv168.project.ui.model.UnitTableComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Unit extends AbstractUserItemData {
    private String name;
    private final int id;
    private static int idCounter = 0;
    private Unit conversionUnit;
    private int ratio;

    public static List<Unit> listOfUnits;

    static {
        listOfUnits = new ArrayList<>(
                List.of(new Unit("gram", null, -1),
                        new Unit("millilitre", null, -1)
                ));
        listOfUnits.add(new Unit("kilogram", listOfUnits.get(0), 1000));
        listOfUnits.add(new Unit("litre", listOfUnits.get(1), 1000));
    }

    public Unit(String name, Unit conversionUnit, int ratio) {
        this.name = name;
        this.id = Unit.idCounter++;
        this.conversionUnit = conversionUnit;
        this.ratio = ratio;
//        listOfUnits.add(this);
    }

    public String getName() {
        return name;
    }

    public Unit getConversionUnit() {
        return conversionUnit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConversionUnit(Unit conversionUnit) {
        this.conversionUnit = conversionUnit;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getRatio() {
        return ratio;
    }

    public int getUnitId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

}
