package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.util.function.Consumer;

/**
 * Panel with entity records in a table.
 */
public abstract class EntityTablePanel<T extends Entity> extends JPanel {
    private final Class<T> type;
    private final JTable table;
    private final Consumer<Integer> onSelectionChange;
    private final EntityTableModel<T> entityTableModel;
    private final Validator<T> entityValidator;
    private final Class<? extends EntityDialog<T>> entityDialog;

    public EntityTablePanel(EntityTableModel<T> entityTableModel, Class<T> type, Validator<T> entityValidator, Class<? extends EntityDialog<T>> entityDialog, Consumer<Integer> onSelectionChange) {
        setLayout(new BorderLayout());
        table = setUpTable(entityTableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        this.type = type;
        this.onSelectionChange = onSelectionChange;
        this.entityTableModel = entityTableModel;
        this.entityValidator = entityValidator;
        this.entityDialog = entityDialog;
    }

    public JTable getTable() {
        return table;
    }

    protected abstract JTable setUpTable(EntityTableModel<T> entityTableModel);

    protected void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }

    public void refresh() {
        entityTableModel.refresh();
    }

    public Validator<T> getEntityValidator() {
        return entityValidator;
    }

    public Class<? extends EntityDialog<T>> getEntityDialog() {
        return entityDialog;
    }

    public Class<T> getType() {
        return type;
    }

    // TODO should i really do this??
    public EntityTableModel<T> getEntityTableModel() {
        return entityTableModel;
    }
}
