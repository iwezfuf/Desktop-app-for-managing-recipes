package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class representing custom table where individual rows are represented by
 * own custom classes.
 *
 * @author Marek Eibel
 */
public class CustomTable extends JTable {

    private DefaultTableModel model;
    private final String name;

    /**
     * Creates new CustomTable.
     *
     * @param tableName name of the table
     */
    public CustomTable(String tableName) {

        this.name = tableName;
        initModel();
        designTable();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the clicked point relative to the table
                Point point = e.getPoint();

                // Get the row and column indices
                int clickedRow = rowAtPoint(point);
                int clickedColumn = columnAtPoint(point);

                // Do something with the clicked row and column
                System.out.println("Clicked on Row: " + clickedRow + ", Column: " + clickedColumn);

                // Example: If you want to get the value in the clicked cell
                RecipeTableComponent value = (RecipeTableComponent) getValueAt(clickedRow, clickedColumn);
                if (value != null) {
                    System.out.println("Recipe name: " + value.getRecipe().getName());
                }
            }
        });
    }

    private void designTable() {

        final int rowHeight = 80;
        final int rowMargin = 20;

        setRowHeight(rowHeight);
        setRowMargin(rowMargin);
    }

    private void initModel() {

        final int INITIAL_COLUMNS_NUMBER = 1;
        final int INITIAL_ROWS_NUMBER = 0;

        model = new DefaultTableModel(INITIAL_ROWS_NUMBER, INITIAL_COLUMNS_NUMBER);
        model.setColumnIdentifiers(new String[]{name});
        setModel(model);
        this.setCellEditor(new CustomCellEditor());
        getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer());
    }

    /**
     * Adds component to the table.
     *
     * @param component component to add
     */
    public void addComponent(AbstractTableComponent component) {

        model.addRow(new AbstractTableComponent[]{component});
    }

    private static class CustomCellRenderer extends JPanel implements TableCellRenderer {
        public CustomCellRenderer() {
            setLayout(new BorderLayout());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (value instanceof AbstractTableComponent customComponent) {

                removeAll();

                JPanel paddedComponent = new JPanel(new BorderLayout());
                paddedComponent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                paddedComponent.add(customComponent, BorderLayout.CENTER);

                add(paddedComponent, BorderLayout.CENTER);

                return this;
            }

            return null;
        }
    }

    /**
     * Gets name of the table.
     *
     * @return name of the table
     */
    @Override
    public String getName() {
        return name;
    }
}
