package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import java.util.List;

public class RecipeCategoryValidator implements Validator<RecipeCategory> {
    @Override
    public ValidationResult validate(RecipeCategory category) {
        var validators = List.of(
                Validator.extracting(
                        RecipeCategory::getName, new StringLengthValidator(1, 50, "RecipeCategory name"))
        );

        return Validator.compose(validators).validate(category);
    }
}
