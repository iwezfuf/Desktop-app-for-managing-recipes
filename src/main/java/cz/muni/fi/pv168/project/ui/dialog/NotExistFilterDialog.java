package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;

import cz.muni.fi.pv168.project.business.model.IngredientFilter;
import cz.muni.fi.pv168.project.business.model.NotExistFilter;
import cz.muni.fi.pv168.project.business.model.Range;


public class NotExistFilterDialog extends EntityDialog<NotExistFilter> {

    public NotExistFilterDialog(cz.muni.fi.pv168.project.model.AbstractFilter filter) {
        super(new Dimension(200, 200));
        JLabel messageLabel = new JLabel("There is no filter for this component.");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(messageLabel);
    }


    @Override
    NotExistFilter getEntity() {
        return null;
    }
}