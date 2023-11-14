package cz.muni.fi.pv168.project.business.service.export.batch;

import cz.muni.fi.pv168.project.business.model.*;

import java.util.Collection;

public record Batch(Collection<Department> departments, Collection<Employee> employees,
                    Collection<Recipe> recipes, Collection<Ingredient> ingredients,
                    Collection<RecipeCategory> recipeCategories, Collection<Unit> units) {
}
