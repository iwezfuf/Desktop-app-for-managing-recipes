package cz.muni.fi.pv168.project.model;

import java.util.ArrayList;
import java.util.List;


public class Unit extends AbstractUserItemData {
    private String name;
    private final int id;
    private static int idCounter = 0;
    private Unit conversionUnit;
    private int conversionRatio;

    public static List<Unit> getListOfUnits() {
        return listOfUnits;
    }

    private static List<Unit> listOfUnits = new ArrayList<>();

    public Unit(String name, Unit conversionUnit, int ratio) {
        this.name = name;
        this.id = Unit.idCounter++;
        this.conversionUnit = conversionUnit;
        this.conversionRatio = ratio;
        if (listOfUnits == null) {
            listOfUnits = new ArrayList<>();
        }
        listOfUnits.add(this);
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

    public void setConversionRatio(int conversionRatio) {
        this.conversionRatio = conversionRatio;
    }

    public int getConversionRatio() {
        return conversionRatio;
    }

    public int getUnitId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean isBaseUnit() {
        return conversionUnit == null;
    }

}
