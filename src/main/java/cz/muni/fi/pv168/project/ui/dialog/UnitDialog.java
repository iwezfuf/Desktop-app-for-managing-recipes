package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import java.awt.*;

public class UnitDialog extends EntityDialog<Unit> {
    private final Unit unit;

    public UnitDialog(Unit unit) {
        super(new Dimension(600, 150));
        this.unit = unit;
    }

    @Override
    Unit getEntity() {
        return null;
    }
}
