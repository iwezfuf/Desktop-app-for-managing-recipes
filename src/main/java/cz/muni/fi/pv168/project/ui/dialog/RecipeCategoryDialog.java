package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.model.Unit;

import java.awt.*;

public class RecipeCategoryDialog extends EntityDialog<Unit> {
    private final RecipeCategory recipeCategory;

    public RecipeCategoryDialog(RecipeCategory recipeCategory) {
        super(new Dimension(600, 150));
        this.recipeCategory = recipeCategory;
    }

    @Override
    Unit getEntity() {
        return null;
    }
}
