package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Recipe;

import java.awt.*;

/**
 * @author Marek Eibel
 */
public class FilterDialog extends EntityDialog<Recipe> {

    FilterDialog(Dimension preferredSize) {
        super(new Dimension(800, 600));
    }

    @Override
    Recipe getEntity() {
        return null;
    }
}
