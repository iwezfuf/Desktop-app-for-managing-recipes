package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.RecipeCategory;

public class RecipeCategoryTableComponent extends AbstractTableComponent {
    private RecipeCategory recipeCategory;

    public RecipeCategoryTableComponent(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;
    }
}
