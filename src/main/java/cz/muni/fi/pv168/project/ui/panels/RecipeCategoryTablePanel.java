package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.function.Consumer;

public class RecipeCategoryTablePanel extends EntityTablePanel<RecipeCategory> {
    public RecipeCategoryTablePanel(EntityTableModel<RecipeCategory> entityTableModel, Validator<RecipeCategory> recipeCategoryValidator, Class<? extends EntityDialog<RecipeCategory>> recipeCategoryDialog, Consumer<Integer> onSelectionChange) {
        super(entityTableModel, RecipeCategory.class, recipeCategoryValidator, recipeCategoryDialog, onSelectionChange);
    }

    @Override
    protected JTable setUpTable(EntityTableModel<RecipeCategory> entityTableModel) {
        var table = new JTable(entityTableModel);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

    protected void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();

        if (count == 1) {
            for (int i : selectionModel.getSelectedIndices()) {
                int modelRow = getTable().convertRowIndexToModel(i);
                var categoryName = this.getEntityTableModel().getEntity(modelRow).getName();
                if (categoryName.equals("No category")) {
                    if (onSelectionChange != null) {
                        onSelectionChange.accept(-1);
                    }
                    return;
                }
            }
        }

        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }
}
