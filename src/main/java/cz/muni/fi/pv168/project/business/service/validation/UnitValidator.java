package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import java.time.Clock;
import java.util.List;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

/**
 * Unit entity {@link Validator}.
 */
public class UnitValidator implements Validator<Unit> {

    @Override
    public ValidationResult validate(Unit model) {
        var validators = List.of(
                extracting(Unit::getName, new StringLengthValidator(1, 50, "Name")),
                extracting(Unit::getAbbreviation, new StringLengthValidator(2, 10, "Abbreviation"))
        );

        return Validator.compose(validators).validate(model);
    }
}
