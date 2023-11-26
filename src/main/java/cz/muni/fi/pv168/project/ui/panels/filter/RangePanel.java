package cz.muni.fi.pv168.project.ui.panels.filter;


import cz.muni.fi.pv168.project.ui.filters.Range;

import cz.muni.fi.pv168.project.ui.model.CustomLabel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class RangePanel extends JPanel {

    private Range range;
    private String msg;
    private String units;

    private final JTextField minRangeField = new JTextField();
    private final JTextField maxRangeField = new JTextField();


    public RangePanel(Range range, String msg, String units) {
        this.range = range;
        this.msg = msg;
        this.units = units;

        init();
    }

    public void fillRange(Range r) {
        minRangeField.setText(String.valueOf(r.getMin()));
        maxRangeField.setText(String.valueOf(r.getMax()));
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        limitComponentToOneRow(minRangeField);
        limitComponentToOneRow(maxRangeField);

        minRangeField.getDocument().addDocumentListener(new DocumentListener() {
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
                int value;
                try {
                    value = Integer.parseInt(minRangeField.getText());
                } catch (NumberFormatException exception) {
                    return;
                }

                range.setMin(value);
            }
        });

        maxRangeField.getDocument().addDocumentListener(new DocumentListener() {
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
                int value;
                try {
                    value = Integer.parseInt(maxRangeField.getText());
                } catch (NumberFormatException exception) {
                    return;
                }

                range.setMax(value);
            }
        });

        Insets spacing = new Insets(5, 5, 5, 5);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        gbc.anchor = GridBagConstraints.PAGE_START;
        CustomLabel label = new CustomLabel(msg, CustomLabel.getTextSizeForCustomLabel(msg));
        label.makeBold();
        this.add(label, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        gbc.anchor = GridBagConstraints.PAGE_START;
        String text = "Choose the range:";
        label = new CustomLabel(text, CustomLabel.getTextSizeForCustomLabel(text));
        label.makeBold();
        this.add(label, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        gbc.anchor = GridBagConstraints.PAGE_START;
        this.add(createRangePanel(minRangeField, "MIN:"), gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        gbc.anchor = GridBagConstraints.PAGE_START;
        this.add(createRangePanel(maxRangeField, "MAX:"), gbc);
    }

    private JPanel createRangePanel(JTextField field, String labelText) {
        JPanel panel = new JPanel(new GridBagLayout());

        Insets spacing = new Insets(5, 5, 5, 5);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        CustomLabel label = new CustomLabel(labelText, CustomLabel.getTextSizeForCustomLabel(labelText));
        label.makeBold();
        panel.add(label, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        panel.add(field, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = spacing; // Set the insets for the component
        label = new CustomLabel(units, CustomLabel.getTextSizeForCustomLabel(units));
        label.makeBold();
        panel.add(label, gbc);

        return panel;
    }

    private static void limitComponentToOneRow(JComponent component) {
        // Limit the preferred and maximum height of the JTextField to one row.
        Dimension preferredSize = component.getPreferredSize();
        preferredSize.height = component.getFontMetrics(component.getFont()).getHeight() + 4; // Adjust as needed.
        component.setPreferredSize(preferredSize);
        component.setMaximumSize(preferredSize);
    }
}
