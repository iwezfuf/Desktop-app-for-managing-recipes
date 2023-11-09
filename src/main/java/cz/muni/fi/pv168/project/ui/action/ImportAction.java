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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class ImportAction extends AbstractAction {
    private final ImportService importService;
    private final Consumer<ImportType> callback;
    private final Component frame;

    public ImportAction(ImportService importService, Consumer<ImportType> callback, Component frame) {
        super("Import", Icons.IMPORT_ICON);
        putValue(SHORT_DESCRIPTION, "Imports data from file");
//        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        this.importService = importService;
        this.callback = callback;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ImportDialog dialog = new ImportDialog(null, (s, importType) -> {
            importService.importData(s, importType);
            callback.accept(importType);
            JOptionPane.showMessageDialog(frame, "Import was done");
        });
        dialog.setVisible(true);



        /*var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        importService.getFormats().forEach(f -> fileChooser.addChoosableFileFilter(new FileFilter(f)));

        int dialogResult = fileChooser.showOpenDialog(frame);

        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            importService.importData(importFile.getAbsolutePath());

            callback.run();
            JOptionPane.showMessageDialog(frame, "Import was done");
        }*/
    }
}
