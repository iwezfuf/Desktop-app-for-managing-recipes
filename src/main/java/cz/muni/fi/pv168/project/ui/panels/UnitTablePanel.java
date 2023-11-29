package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
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

    protected void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();

        // disable edit and delete for base units
        if (count == 1) {
            for (int i : selectionModel.getSelectedIndices()) {
                int modelRow = getTable().convertRowIndexToModel(i);
                var unitName = this.getEntityTableModel().getEntity(modelRow).getName();
                if (unitName.equals("gram") || unitName.equals("liter") || unitName.equals("piece")) {
                    if (onSelectionChange != null) {
                        onSelectionChange.accept(-1);
                    }
                    return;
                }
            }
        }

        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }

}
