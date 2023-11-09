package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.IngredientFilter;
import cz.muni.fi.pv168.project.business.model.Range;
import cz.muni.fi.pv168.project.model.AbstractFilter;

import java.awt.*;

public class IngredientFilterDialog extends EntityDialog<IngredientFilter> {

    private final Range nutritionalRange = new Range(0, 1000000000);
    private final RangePanel nutritionalValuePanel = new RangePanel(nutritionalRange, "Filter ingredients by their nutritional value", "kcal");

    public IngredientFilterDialog(AbstractFilter filter) {
        super(new Dimension(800, 600));
        this.add(nutritionalValuePanel);
        fillFilter(filter);
    }

    private void fillFilter(AbstractFilter filter) {
        if (filter == null) {
            return;
        }
        IngredientFilter inf = (IngredientFilter) filter;
        nutritionalValuePanel.fillRange(nutritionalRange);
        nutritionalRange.setMin(inf.getNutritionValueRange().getMin());
        nutritionalRange.setMax(inf.getNutritionValueRange().getMax());
    }

    @Override
    IngredientFilter getEntity() {
        return new IngredientFilter(nutritionalRange);
    }
}
