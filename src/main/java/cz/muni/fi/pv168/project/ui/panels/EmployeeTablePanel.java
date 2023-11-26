package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Gender;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
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
 * Panel with employee records in a table.
 */
public class EmployeeTablePanel extends EntityTablePanel<Employee> {
    public EmployeeTablePanel(EntityTableModel<Employee> entityTableModel, Validator<Employee> employeeValidator, Class<? extends EntityDialog<Employee>> employeeDialog, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, Employee.class, employeeValidator, employeeDialog, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<Employee> entityTableModel) {
        var table = new JTable(entityTableModel);

        var genderRenderer = new GenderRenderer();
        var departmentRenderer = new DepartmentRenderer();

        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        var genderComboBox = new JComboBox<>(Gender.values());
        genderComboBox.setRenderer(genderRenderer);
        table.setDefaultEditor(Gender.class, new DefaultCellEditor(genderComboBox));

        table.setDefaultRenderer(Gender.class, genderRenderer);
        table.setDefaultRenderer(Department.class, departmentRenderer);
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());

        return table;
    }
}
