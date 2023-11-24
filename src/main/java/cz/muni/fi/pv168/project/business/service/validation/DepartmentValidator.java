package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthAndCharValidator;

import java.util.List;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

/**
 * Department entity {@link Validator}
 */
public class DepartmentValidator implements Validator<Department> {
    @Override
    public ValidationResult validate(Department department) {
        var validators = List.of(
                extracting(
                        Department::getName, new StringLengthAndCharValidator(1, 50, "Department name")),
                extracting(
                        Department::getNumber, new StringLengthAndCharValidator(2, 10, "Department number"))
        );

        return Validator.compose(validators).validate(department);
    }
}
