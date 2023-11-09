package cz.muni.fi.pv168.project.business.model;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import cz.muni.fi.pv168.project.model.AbstractFilter;

public class IngredientFilter implements AbstractFilter {

    private Range nutritionValueRange;

    public IngredientFilter(Range nutritionValueRange) {
        this.nutritionValueRange = nutritionValueRange;
    }

    private boolean evaluateIngredient(Ingredient ingredient) {
        return nutritionValueRange.isValueInRange(ingredient.getNutritionalValue());
    }

    public Range getNutritionValueRange() {
        return nutritionValueRange;
    }

    @Override
    public RowFilter<DefaultTableModel, Object> getRowFilter() {
        return new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ?> entry) {

                Object columnValue = entry.getValue(0);
                return evaluateIngredient((Ingredient)columnValue);
            }
        };
    }
}
