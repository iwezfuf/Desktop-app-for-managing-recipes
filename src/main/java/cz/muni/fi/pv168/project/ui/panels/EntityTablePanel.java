package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.action.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.EditAction;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.filters.AbstractFilter;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.renderers.ColorRenderer;
import cz.muni.fi.pv168.project.ui.renderers.RecipeCategoryRenderer;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProviderWithCrud;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Panel with entity records in a table.
 */
public abstract class EntityTablePanel<T extends Entity> extends JPanel {
    private final Class<T> type;
    private final JTable table;
    protected final Consumer<Integer> onSelectionChange;
    private final EntityTableModel<T> entityTableModel;
    private final Validator<T> entityValidator;
    private final Class<? extends EntityDialog<T>> entityDialog;

    private boolean activeFilter;
    private final boolean filterable;
    private final TableRowSorter<EntityTableModel<T>> rowSorter;
    protected EntityTableModelProviderWithCrud provider;

    /**
     * Creates an EntityTablePanel containing the table.
     *
     * @param entityTableModel
     * @param type
     * @param entityValidator
     * @param entityDialog
     * @param onSelectionChange
     */
    public EntityTablePanel(EntityTableModel<T> entityTableModel, Class<T> type, Validator<T> entityValidator, Class<? extends EntityDialog<T>> entityDialog, Consumer<Integer> onSelectionChange, EntityTableModelProviderWithCrud provider) {
        this.provider = provider;

        setLayout(new BorderLayout());
        table = setUpTable(entityTableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        this.type = type;
        this.onSelectionChange = onSelectionChange;
        this.entityTableModel = entityTableModel;
        this.entityValidator = entityValidator;
        this.entityDialog = entityDialog;

        // Filtering
        this.activeFilter = false;
        this.filterable = true;
        this.rowSorter = new TableRowSorter<>(entityTableModel);
        rowSorter.setComparator(0, Comparator.naturalOrder());
        table.setRowSorter(rowSorter);

        // popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        popupMenu.add(new EditAction<T>(this, provider));
        popupMenu.add(new DeleteAction(table));
        table.setComponentPopupMenu(popupMenu);

        table.setDefaultRenderer(Color.class, new ColorRenderer());
        table.setDefaultRenderer(RecipeCategory.class, new RecipeCategoryRenderer());
    }

    /**
     * Applies given filter to the table rows. If the table does not allow filtering,
     * nothing happens.
     *
     * @param filter filter to use
     */
    public void applyFilter(AbstractFilter filter) {

        if (!filterable) {
            return;
        }

        activeFilter = true;
        RowFilter<EntityTableModel<T>, Integer> rowFilter = filter.getRowFilter();
        this.rowSorter.setRowFilter(rowFilter);
        rowSorter.setComparator(0, Comparator.naturalOrder());
        this.table.setRowSorter(rowSorter);
    }

    /**
     * Cancels the current applied filter. If the table does not allow filtering,
     * nothing happens.
     */
    public void cancelFilter() {

        if (!filterable) {
            return;
        }

        this.rowSorter.setRowFilter(null);
        activeFilter = false;
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

    public EntityTableModel<T> getEntityTableModel() {
        return entityTableModel;
    }

    public EntityDialog<T> createDialog(T instance, EntityTableModelProviderWithCrud entityTableModelProviderWithCrud) {
        try {
            return entityDialog.getConstructor(type, EntityTableModelProviderWithCrud.class, Validator.class).newInstance(instance, entityTableModelProviderWithCrud, entityValidator);
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

    public TableRowSorter<EntityTableModel<T>> getRowSorter() {
        return rowSorter;
    }
}
