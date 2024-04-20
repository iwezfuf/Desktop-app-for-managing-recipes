package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;

import javax.swing.*;

/**
 * The AbstractFilter interface defines a method to obtain a RowFilter that can be used to filter rows\
 * in a DefaultTableModel.
 *
 * @author Marek Eibel
 */
public interface AbstractFilter {

    /**
     * Gets a RowFilter for filtering rows in a DefaultTableModel.
     *
     * @return A RowFilter that can be used to filter rows in a DefaultTableModel.
     */
    <T extends Entity> RowFilter<EntityTableModel<T>, Integer> getRowFilter();
}

