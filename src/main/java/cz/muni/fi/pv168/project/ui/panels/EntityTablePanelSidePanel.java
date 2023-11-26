package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public abstract class EntityTablePanelSidePanel<T extends Entity> extends EntityTablePanel<T>{

    private JScrollPane sideScrollPane;
    private EntityTableModelProvider etmp;

    public EntityTablePanelSidePanel(EntityTableModel<T> entityTableModel, Class<T> type, Validator<T> entityValidator, Class<? extends EntityDialog<T>> entityDialog, Consumer<Integer> onSelectionChange, EntityTableModelProvider etmp) {
        super(entityTableModel, type, entityValidator, entityDialog, onSelectionChange);

        this.etmp = etmp;
    }

    @Override
    protected JTable setUpTable(EntityTableModel<T> entityTableModel) {
        var table = new JTable(entityTableModel);
        this.sideScrollPane = new JScrollPane();
        this.add(sideScrollPane, BorderLayout.WEST);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        customizeTable(table);
        return table;
    }

    protected void customizeTable(JTable table) {

    }

    public EntityTableModelProvider getEtmp() {
        return etmp;
    }

    public JScrollPane getSideScrollPane() {
        return sideScrollPane;
    }
}
