package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;
import cz.muni.fi.pv168.project.business.model.Recipe;

import java.util.List;

/**
 * Employee entity {@link Validator}.
 */
public class RecipeValidator implements Validator<Recipe> {

    @Override
    public ValidationResult validate(Recipe model) {
        var validators = List.of(
                Validator.extracting(Recipe::getName, new StringLengthValidator(2, 20000, "Name")),
                Validator.extracting(Recipe::getDescription, new StringLengthValidator(2, 300, "Description"))
        );

        return Validator.compose(validators).validate(model);
    }
}
