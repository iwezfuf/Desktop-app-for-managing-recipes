package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Employee;

import java.time.Clock;

public class EmployeeAgeValidator implements Validator<Employee> {

    private final Clock clock;

    public EmployeeAgeValidator(Clock clock) {
        // Using the Clock abstraction, we can easily simulate the current date in tests
        this.clock = clock;
    }

    @Override
    public ValidationResult validate(Employee employee) {

        // to get the current date, use: LocalDate.now(clock)

        // TODO implement
        return ValidationResult.success();
    }
}
