package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;

public class UnitTableComponent extends AbstractTableComponent {

    private final Unit unit;

    public UnitTableComponent(Unit unit) {
        this.unit = unit;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 15));
        setBackground(Color.getHSBColor(0.2f, 0.4f, 0.9f));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        CustomLabel nameLabel = new CustomLabel(unit.getFullName(), 40);
        nameLabel.makeBold();
        nameLabel.setFontSize(24);

        CustomLabel descriptionLabel;
        if (!unit.isBaseUnit()) {
            descriptionLabel = new CustomLabel(" ≈ " + unit.getConversionRatio() + " " + unit.getConversionUnit().getName(), 70);
        } else {
            descriptionLabel = new CustomLabel("Base unit", 70);
        }
        descriptionLabel.makeItalic();
        descriptionLabel.setFontSize(14);

        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);

        GridBagConstraints textPanelConstraints = new GridBagConstraints();
        textPanelConstraints.fill = GridBagConstraints.BOTH;
        textPanel.setOpaque(false);
        add(textPanel, textPanelConstraints);
    }

    public Unit getUnit() {
        return unit;
    }
}
