package cz.muni.fi.pv168.project.business.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import cz.muni.fi.pv168.project.model.AbstractFilter;

public class NotExistFilter implements AbstractFilter {

    @Override
    public RowFilter<DefaultTableModel, Object> getRowFilter() {
        return new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ?> entry) {
                return false;
            }
        };
    }
}