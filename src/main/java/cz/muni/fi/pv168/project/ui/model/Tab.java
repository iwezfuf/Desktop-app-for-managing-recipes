package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;

/**
 * Represents a tab inside tabbed pane.
 *
 * @author Marek Eibel
 */
public class Tab {

    private JTable table;
    private String name;
    private Icon icon;
    private String tooltip;

    /**
     * Creates new tab.
     *
     * @param table table with data
     * @param name name for the tab
     * @param icon icon for the tab
     * @param tooltip tooltip for the tab
     */
    public Tab(JTable table, String name, Icon icon, String tooltip) {
        this.table = table;
        this.name = name;
        this.icon = icon;
        this.tooltip = tooltip;
    }

    /**
     * Creates new tab.
     *
     * @param table table with data
     * @param name name for the tab
     * @param tooltip tooltip for the tab
     */
    public Tab(JTable table, String name, String tooltip) {
        this.table = table;
        this.name = name;
        this.icon = null;
        this.tooltip = tooltip;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
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
}
