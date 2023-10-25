package cz.muni.fi.pv168.project.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    RowFilter<DefaultTableModel, Object> getRowFilter();
}

