package cz.muni.fi.pv168.project.business.service.validation.common;

import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

public final class UnitRatioValidator extends PropertyValidator<Float> {

    public UnitRatioValidator(String name) {
        super(name);
    }

    @Override
    public ValidationResult validate(Float value) {
        var result = new ValidationResult();

        if (value == null) {
            result.add("'%s' cannot be null".formatted(getName()));
        }

        return result;
    }
}
