package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.service.export.ImportStrategy;
import cz.muni.fi.pv168.project.business.service.export.MergeImportStrategy;
import cz.muni.fi.pv168.project.business.service.export.ReplaceImportStrategy;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.function.BiConsumer;

/**
 * Represents the ImportDialog class.
 */
public class ImportDialog extends JDialog {

    private final JComboBox<ImportType> importTypeComboBox;
    private final JTextField selectedFileField;

    /**
     * Two possible types of importing.
     */
    private enum ImportType {
        MERGE,
        REPLACE
    }

    public ImportDialog(JFrame parent, BiConsumer<String, ImportStrategy> onDispose) {

        super(parent, "Import Data", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel importTypeLabel = new JLabel("Import Type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(importTypeLabel, gbc);

        importTypeComboBox = new JComboBox<>(ImportType.values());
        importTypeComboBox.setPreferredSize(new Dimension(100, importTypeComboBox.getPreferredSize().height));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(importTypeComboBox, gbc);

        JButton selectFileButton = new JButton("Select File");
        selectedFileField = new JTextField(20);
        selectedFileField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(selectFileButton, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(selectedFileField, gbc);

        Box buttonBox = Box.createHorizontalBox();
        addButtons(onDispose, buttonBox);
        buttonBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonBox, gbc);

        getContentPane().add(panel);
        pack();

        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(ImportDialog.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFileField.setText(selectedFile.getAbsolutePath());
            }
        });
    }

    private void addButtons(BiConsumer<String, ImportStrategy> onDispose, Box buttonBox) {

        JButton importButton = new JButton("Import Data");
        importButton.addActionListener(e -> {
            ImportStrategy importStrategy = (importTypeComboBox.getSelectedItem() == ImportType.MERGE) ? new MergeImportStrategy() : new ReplaceImportStrategy();
            String selectedFile = selectedFileField.getText();
            if (selectedFile == null || selectedFile.isEmpty()) {
                JOptionPane.showMessageDialog(ImportDialog.this, "Please select a file to import from.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            onDispose.accept(selectedFile, importStrategy);
            dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonBox.add(importButton);
        buttonBox.add(cancelButton);
    }
}
