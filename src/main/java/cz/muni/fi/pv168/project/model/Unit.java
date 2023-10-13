package cz.muni.fi.pv168.project.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// TODO conversion inbetween standardized units and our custom units
public class Unit {
    private String name;
    private final int id;
    private static int idCounter = 0;

    public static List<Unit> listOfUnits;

    static {
        listOfUnits = new ArrayList<>(
                List.of(new Unit("Kg"),
                        new Unit("Litre")
                ));
    }

    public Unit(String name) {
        this.name = name;
        this.id = Unit.idCounter++;
//        listOfUnits.add(this);
    }

    public String getName() {
        return name;
    }

    public int getUnitId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

}
