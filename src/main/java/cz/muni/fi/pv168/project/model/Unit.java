package cz.muni.fi.pv168.project.model;

import java.util.ArrayList;
import java.util.List;


public class Unit extends AbstractUserItemData {
    private String name;
    private final int id;
    private static int idCounter = 0;
    private Unit conversionUnit;
    private int conversionRatio;

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
        this.conversionRatio = ratio;
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

}
