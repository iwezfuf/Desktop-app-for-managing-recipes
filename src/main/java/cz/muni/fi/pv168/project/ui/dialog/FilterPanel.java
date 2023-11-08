package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.RecipeCategory;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class FilterPanel<E> extends JPanel {

    private final JComboBox<E> comboBox = new JComboBox<>();
    private final JButton addButton = new JButton(Icons.ADD_ICON);
    private final JPanel filterPanel = new JPanel(new GridBagLayout());
    private Set<E> filterSet;
    private String msg1;
    private String msg2;

    public FilterPanel(Set<E> filterSet, List<E> comboBoxData, String msg1, String msg2) {
        this.filterSet = filterSet;
        this.msg1 = msg1;
        this.msg2 = msg2;

        init();

        for (E element : comboBoxData) {
            comboBox.addItem(element);
        }
    }

    private void init() {
        this.setLayout(new GridBagLayout());

        this.addButton.addActionListener(e -> {
            E selectedItem = (E) comboBox.getSelectedItem();
            if (filterSet.contains(selectedItem)) {
                return;
            }
            filterSet.add(selectedItem);
            addToFilterPanel(selectedItem);
        });

        JPanel panel = new JPanel();
        BoxLayout bl2 = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(bl2);

        panel.add(comboBox);
        panel.add(addButton);
        addToPanel(new JLabel("Include " + msg1 + " in your filter:"), 1);
        addToPanel(panel, 1);

        var scrollPane = new JScrollPane(this.filterPanel);
        addToPanel(new JLabel("Used " + msg2 + ": "), 1);
        addToPanel(scrollPane, 4);
    }

    private void addToPanel(JComponent component, int weighty) {
        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.fill = GridBagConstraints.BOTH;
        componentConstraints.gridy = GridBagConstraints.RELATIVE;
        componentConstraints.gridx = 0;
        componentConstraints.weightx = 1;
        componentConstraints.weighty = weighty;
        Insets spacing = new Insets(5, 5, 5, 5);
        componentConstraints.insets = spacing; // Set the insets for the component
        this.add(component, componentConstraints);
    }

    public void fillFilterPanel(Set<E> itemSet) {
        for (E item : itemSet) {
            addToFilterPanel(item);
        }
    }

    private void addToFilterPanel(E item) {
        var panel = new JPanel(new GridBagLayout());
        var removeButton = new JButton(Icons.DELETE_ICON);
        removeButton.addActionListener(e -> {
            filterSet.remove(item);
            filterPanel.remove(panel);
            filterPanel.revalidate();
            filterPanel.repaint();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        panel.add(new JLabel(item.toString()), gbc);

        panel.add(removeButton);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.weightx = 1;

        filterPanel.add(panel, gbc);
        filterPanel.revalidate();
        filterPanel.repaint();
    }
}
