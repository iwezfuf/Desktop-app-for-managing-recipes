package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;

/**
 * Represents a tab inside tabbed pane.
 *
 * @author Marek Eibel
 */
public class Tab {

    private JComponent component;
    private String name;
    private Icon icon;
    private String tooltip;

    /**
     * Creates new tab.
     *
     * @param component component to display
     * @param name name for the tab
     * @param icon icon for the tab
     * @param tooltip tooltip for the tab
     */
    public Tab(JComponent component, String name, Icon icon, String tooltip) {
        this.component = component;
        this.name = name;
        this.icon = icon;
        this.tooltip = tooltip;
    }

    /**
     * Creates new tab.
     *
     * @param component component to display
     * @param name name for the tab
     * @param tooltip tooltip for the tab
     */
    public Tab(JComponent component, String name, String tooltip) {
        this.component = component;
        this.name = name;
        this.icon = null;
        this.tooltip = tooltip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public JComponent getComponent() {
        return component;
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }
}
