package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import java.util.function.Consumer;

public class UnitTablePanel extends EntityTablePanel<Unit> {
    public UnitTablePanel(EntityTableModel<Unit> entityTableModel, Validator<Unit> unitValidator, Class<? extends EntityDialog<Unit>> unitDialog, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, Unit.class, unitValidator, unitDialog, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Unit> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

}
