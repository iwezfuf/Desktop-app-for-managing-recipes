package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Gender;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.DepartmentListModel;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.renderers.DepartmentRenderer;
import cz.muni.fi.pv168.project.ui.renderers.GenderRenderer;
import cz.muni.fi.pv168.project.ui.renderers.LocalDateRenderer;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Panel with entity records in a table.
 */
public abstract class EntityTablePanel<T extends Entity> extends JPanel {

    private final JTable table;
    private final Consumer<Integer> onSelectionChange;
    private final EntityTableModel<T> entityTableModel;

    public EntityTablePanel(EntityTableModel<T> entityTableModel, Consumer<Integer> onSelectionChange) {
        setLayout(new BorderLayout());
        table = setUpTable(entityTableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        this.onSelectionChange = onSelectionChange;
        this.entityTableModel = entityTableModel;
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
}
