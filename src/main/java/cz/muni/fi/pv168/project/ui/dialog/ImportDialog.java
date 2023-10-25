package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.ImportType;
import cz.muni.fi.pv168.project.ui.action.ImportAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImportDialog extends JDialog {
    private final JComboBox<ImportType> importTypeComboBox;
    private final JTextField selectedFileField;

    public ImportDialog(JFrame parent) {
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

        JButton importButton = new JButton("Import Data");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImportType importType = (ImportType) importTypeComboBox.getSelectedItem();
                String selectedFile = selectedFileField.getText();
                if (selectedFile == null || selectedFile.isEmpty()) {
                    JOptionPane.showMessageDialog(ImportDialog.this, "Please select file to import from.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ImportAction.importData(importType, selectedFile);
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonBox.add(importButton);
        buttonBox.add(cancelButton);
        buttonBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonBox, gbc);

        getContentPane().add(panel);
        pack();

        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(ImportDialog.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
    }
}
