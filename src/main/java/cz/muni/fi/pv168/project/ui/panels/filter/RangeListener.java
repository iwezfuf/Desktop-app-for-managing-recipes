package cz.muni.fi.pv168.project.ui.panels.filter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class RangeListener implements DocumentListener {

    private RangePanel rangePanel;
    private int blankValue;
    private JTextField rangeField;
    private Consumer<Integer> setMethod;

    public RangeListener(RangePanel rp, JTextField rangeField, Consumer<Integer> setMethod, int blankValue) {
        this.rangePanel = rp;
        this.blankValue = blankValue;
        this.rangeField = rangeField;
        this.setMethod = setMethod;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (rangeField.getText().length() == 0) {
            rangeField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            rangePanel.setReadyToFilter(true);
            this.setMethod.accept(blankValue);
            return;
        }

        int value;
        try {
            value = Integer.parseInt(rangeField.getText());
        } catch (NumberFormatException exception) {
            rangeField.setBorder(BorderFactory.createLineBorder(Color.RED));
            rangePanel.setReadyToFilter(false);
            return;
        }

        rangeField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        rangePanel.setReadyToFilter(true);
        this.setMethod.accept(value);
    }
}
