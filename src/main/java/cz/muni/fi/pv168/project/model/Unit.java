package cz.muni.fi.pv168.project.model;

// TODO conversion inbetween standardized units and our custom units
public class Unit {
    private String name;
    private int unitId;

    public Unit(String name, int unitId) {
        this.name = name;
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public int getUnitId() {
        return unitId;
    }
}
