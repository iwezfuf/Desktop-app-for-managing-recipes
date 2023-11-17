package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Gender;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.model.LocalDateModel;
import cz.muni.fi.pv168.project.ui.renderers.DepartmentRenderer;
import cz.muni.fi.pv168.project.ui.renderers.GenderRenderer;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.time.LocalDate;
import java.util.Objects;

public final class EmployeeDialog extends EntityDialog<Employee> {

    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final ComboBoxModel<Gender> genderModel = new DefaultComboBoxModel<>(Gender.values());
    private final DateModel<LocalDate> birthDateModel = new LocalDateModel();
    private final ComboBoxModel<Department> departmentModel;


    public EmployeeDialog(
            Employee employee,
            EntityTableModelProvider entityTableModelProvider,
            Validator<Employee> entityValidator) {
        super(employee, entityTableModelProvider, Objects.requireNonNull(entityValidator));
        departmentModel = new ComboBoxModelAdapter<>(entityTableModelProvider.getDepartmentListModel());
        setValues();
        addFields();
    }

    private void setValues() {
        firstNameField.setText(entity.getFirstName());
        lastNameField.setText(entity.getLastName());
        genderModel.setSelectedItem(entity.getGender());
        departmentModel.setSelectedItem(entity.getDepartment());
        birthDateModel.setValue(entity.getBirthDate());
    }

    private void addFields() {
        var genderComboBox = new JComboBox<>(genderModel);
        genderComboBox.setRenderer(new GenderRenderer());

        var deparmentComboBox = new JComboBox<>(departmentModel);
        deparmentComboBox.setRenderer(new DepartmentRenderer());

        add("First Name:", firstNameField);
        add("Last Name:", lastNameField);
        add("Gender:", genderComboBox);
        add("Birth Date:", new JDatePicker(birthDateModel));
        add("Department:", deparmentComboBox);
    }

    @Override
    Employee getEntity() {
        entity.setFirstName(firstNameField.getText());
        entity.setLastName(lastNameField.getText());
        entity.setGender((Gender) genderModel.getSelectedItem());
        entity.setDepartment((Department) departmentModel.getSelectedItem());
        entity.setBirthDate(birthDateModel.getValue());
        return entity;
    }
}
