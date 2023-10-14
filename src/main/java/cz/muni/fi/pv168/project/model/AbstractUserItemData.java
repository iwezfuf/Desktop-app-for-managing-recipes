package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.dialog.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.AbstractTableComponent;

public abstract class AbstractUserItemData {
    Class<? extends AbstractTableComponent> tableComponentClass;
    Class<? extends EntityDialog> dialogClass;

    public Class<? extends AbstractTableComponent> getTableComponentClass() {
        return tableComponentClass;
    }

    public Class<? extends EntityDialog> getDialogClass() {
        return dialogClass;
    }
}
