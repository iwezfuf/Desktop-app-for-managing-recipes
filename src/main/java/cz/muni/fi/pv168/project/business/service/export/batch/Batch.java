package cz.muni.fi.pv168.project.business.service.export.batch;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.Unit;

import java.util.Collection;

public record Batch(Collection<Recipe> recipes, Collection<Ingredient> ingredients, Collection<Unit> units) {
}
