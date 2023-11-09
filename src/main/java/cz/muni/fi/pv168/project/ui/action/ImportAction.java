package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Filter;
import cz.muni.fi.pv168.project.business.model.ImportType;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.ui.dialog.ImportDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.FileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public final class ImportAction extends AbstractAction {
    private final ImportService importService;
    private final Runnable callback;
    private final Component frame;

    public ImportAction(ImportService importService, Runnable callback, Component frame) {
        super("Import", Icons.IMPORT_ICON);
        putValue(SHORT_DESCRIPTION, "Imports data from file");
//        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        this.importService = importService;
        this.callback = callback;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        importService.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new FileFilter(f)));

        int dialogResult = fileChooser.showOpenDialog(frame);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            importService.importData(importFile.getAbsolutePath());

            callback.run();
            JOptionPane.showMessageDialog(frame, "Import was done");
        }
    }

//        ImportDialog dialog = new ImportDialog(null);
//        dialog.setVisible(true);

    public static void importData(ImportType importType, String selectedFile) {
        System.out.println("import from file: " + selectedFile + " with type: " + importType);
    }
}
