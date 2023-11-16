package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import java.util.List;

public class UnitValidator implements Validator<Unit> {
    @Override
    public ValidationResult validate(Unit unit) {
        var validators = List.of(
                Validator.extracting(
                        Unit::getName, new StringLengthValidator(1, 50, "Unit name"))
        );

        return Validator.compose(validators).validate(unit);
    }
}
