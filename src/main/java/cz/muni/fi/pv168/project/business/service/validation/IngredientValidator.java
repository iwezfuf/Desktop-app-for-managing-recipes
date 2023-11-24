package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthAndCharValidator;

import java.util.List;

public class IngredientValidator implements Validator<Ingredient> {
    @Override
    public ValidationResult validate(Ingredient ingredient) {
        var validators = List.of(
                Validator.extracting(
                        Ingredient::getName, new StringLengthAndCharValidator(1, 50, "Ingredient name"))
        );

        return Validator.compose(validators).validate(ingredient);
    }
}
