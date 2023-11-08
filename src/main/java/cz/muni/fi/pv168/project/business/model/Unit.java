package cz.muni.fi.pv168.project.business.model;

import java.util.ArrayList;
import java.util.List;


public class Unit extends Entity {
    private String name;
    private final int id;
    private static int idCounter = 0;
    private Unit conversionUnit;
    private int conversionRatio;
    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    public static List<Unit> getListOfUnits() {
        return listOfUnits;
    }

    private static List<Unit> listOfUnits = new ArrayList<>();

    public Unit(String name, Unit conversionUnit, int ratio, String abbreviation) {
        this.name = name;
        this.id = Unit.idCounter++;
        this.conversionUnit = conversionUnit;
        this.conversionRatio = ratio;
        this.abbreviation = abbreviation;
        if (listOfUnits == null) {
            listOfUnits = new ArrayList<>();
        }
        listOfUnits.add(this);
    }

    public Unit() {
        this.name = "";
        this.id = Unit.idCounter++;
        this.conversionUnit = null;
        this.conversionRatio = 0;
        this.abbreviation = "";
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

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public String getFullName() {
        return getName() + " (" + getAbbreviation() + ")";
    }

    public boolean isBaseUnit() {
        return conversionUnit == null;
    }

    @Override
    public String getGuid() {
        return "unit-" + id;
    }
}
