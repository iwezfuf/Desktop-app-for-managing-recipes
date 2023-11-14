package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Unit tests for {@link EmployeeAgeValidator}.
 */
class EmployeeAgeValidatorTest {

    // Using this, we simulate that the current date is 1.1.2018
    private static final Clock EPOCH_CLOCK = Clock.fixed(LocalDate.of(2018, 1, 1)
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    private EmployeeAgeValidator employeeAgeValidator;

    @BeforeEach
    void setUp() {
        employeeAgeValidator = new EmployeeAgeValidator(EPOCH_CLOCK);
    }

    @Test
    void validateSuccess() {
        // TODO implement this test
    }

    @Test
    void validateFails() {
        // TODO implement this test
    }

    private Employee setUpEmployee(LocalDate birthDate) {
        return new Employee(
                "John",
                "Doe",
                Gender.FEMALE,
                birthDate,
                new Department("IT", "Information Technology", "69")
        );
    }
}