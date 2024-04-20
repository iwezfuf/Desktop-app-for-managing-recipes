package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;

import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.function.Consumer;

public abstract class EntityTablePanelSidePanel<T extends Entity> extends EntityTablePanel<T> {
    private JScrollPane sideScrollPane;
    private JLabel jLabel;

    public EntityTablePanelSidePanel(EntityTableModel<T> entityTableModel, Class<T> type, Validator<T> entityValidator, Class<? extends EntityDialog<T>> entityDialog, Consumer<Integer> onSelectionChange, EntityTableModelProviderWithCrud etmp) {
        super(entityTableModel, type, entityValidator, entityDialog, onSelectionChange, etmp);

        getEntityTableModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                refreshStatics();
            }
        });

        getRowSorter().addRowSorterListener(new RowSorterListener() {
            @Override
            public void sorterChanged(RowSorterEvent e) {
                refreshStatics();
            }
        });


    }

    @Override
    protected JTable setUpTable(EntityTableModel<T> entityTableModel) {
        var table = new JTable(entityTableModel);
        this.sideScrollPane = new JScrollPane();
        this.add(sideScrollPane, BorderLayout.WEST);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        JPanel sideNorth = new JPanel();
        this.jLabel = new JLabel(("Count: " + entityTableModel.getEntities().size()));
        this.add(sideNorth, BorderLayout.NORTH);
        sideNorth.add(jLabel);
        return table;
    }

    public void refreshStatics() {
        jLabel.setText("Count: " + getRowSorter().getViewRowCount() + "/" + getEntityTableModel().getRowCount());
    }

    public EntityTableModelProviderWithCrud getEtmp() {
        return provider;
    }

    public JScrollPane getSideScrollPane() {
        return sideScrollPane;
    }
}
