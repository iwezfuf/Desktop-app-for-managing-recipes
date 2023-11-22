package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
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

    /**
     * Creates an EntityTablePanel with a side panel.
     *
     * @param entityTableModel
     * @param type
     * @param entityValidator
     * @param entityDialog
     * @param onSelectionChange
     * @param frameHeight height of the main frame
     */
    public EntityTablePanel(EntityTableModel<T> entityTableModel, Class<T> type, Validator<T> entityValidator, Class<? extends EntityDialog<T>> entityDialog, Consumer<Integer> onSelectionChange, int frameHeight) {

        setLayout(new BorderLayout());

        JPanel panel;
        panel = setUpTableWithSidePanel(entityTableModel, frameHeight);
        add(new JScrollPane(panel), BorderLayout.CENTER);
        this.table = findTableInPanel(panel);

        this.type = type;
        this.onSelectionChange = onSelectionChange;
        this.entityTableModel = entityTableModel;
        this.entityValidator = entityValidator;
        this.entityDialog = entityDialog;
    }

    /**
     * Creates an EntityTablePanel containing the table.
     *
     * @param entityTableModel
     * @param type
     * @param entityValidator
     * @param entityDialog
     * @param onSelectionChange
     */
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

    private JTable findTableInPanel(JPanel panel) {

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JScrollPane) {
                Component viewportView = ((JScrollPane) component).getViewport().getView();
                if (viewportView instanceof JTable) {
                    return (JTable) viewportView;
                }
            }
        }
        return null;
    }

    public JTable getTable() {
        return table;
    }

    protected abstract JTable setUpTable(EntityTableModel<T> entityTableModel);

    protected abstract JPanel setUpTableWithSidePanel(EntityTableModel<T> entityTableModel, int frameHeight);

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

    public EntityDialog<T> createDialog(T instance, EntityTableModelProvider entityTableModelProvider) {
        try {
            return entityDialog.getConstructor(type, EntityTableModelProvider.class, Validator.class).newInstance(instance, entityTableModelProvider, entityValidator);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getEntityInstance() {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
