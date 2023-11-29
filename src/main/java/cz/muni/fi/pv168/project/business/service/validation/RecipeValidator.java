package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthAndCharValidator;

import java.util.List;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

/**
 * Recipe entity {@link Validator}.
 */
public class RecipeValidator implements Validator<Recipe> {

    @Override
    public ValidationResult validate(Recipe model) {
        var validators = List.of(
                extracting(Recipe::getName, new StringLengthAndCharValidator(1, 50, "Name")),
                extracting(Recipe::getDescription, new StringLengthAndCharValidator(1, 1000, "Description")),
                extracting(Recipe::getInstructions, new StringLengthAndCharValidator(1, 10000, "Instructions"))
        );

        return Validator.compose(validators).validate(model);
    }
}
