package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.business.model.Employee;

import javax.swing.JLabel;

public class EmployeeRenderer extends AbstractRenderer<Employee> {

    public EmployeeRenderer() {
        super(Employee.class);
    }

    @Override
    protected void updateLabel(JLabel label, Employee value) {
        label.setText(String.format("%s %s", value.getFirstName(), value.getLastName()));
    }
}
