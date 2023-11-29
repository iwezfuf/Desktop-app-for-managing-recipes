package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.validation.common.FloatUnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthAndCharValidator;

import java.util.List;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

/**
 * Unit entity {@link Validator}.
 */
public class UnitValidator implements Validator<Unit> {

    @Override
    public ValidationResult validate(Unit model) {
        var validators = List.of(
                extracting(Unit::getName, new StringLengthAndCharValidator(1, 50, "Name")),
                extracting(Unit::getAbbreviation, new StringLengthAndCharValidator(1, 10, "Abbreviation")),
                extracting(Unit::getConversionRatio, new FloatUnitValidator("ConversionRatio"))
        );

        return Validator.compose(validators).validate(model);
    }
}
