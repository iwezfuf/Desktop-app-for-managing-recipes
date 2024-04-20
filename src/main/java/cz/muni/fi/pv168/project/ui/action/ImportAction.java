package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.service.export.AsyncImportService;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.ui.dialog.ImportDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action responsible for importing all the data to a file.
 *
 * <p>This class only handles the UI-related part of the import (choosing a file and choosing type of import)
 * and delegates the import itself to an instance of {@link ImportService}.
 */
public final class ImportAction extends AbstractAction {
    private final ImportService importService;
    private final Runnable refreshCallback;
    private final Component frame;

    public ImportAction(ImportService importService, Runnable refreshCallback, Component frame) {
        super("Import", Icons.IMPORT_ICON);
        putValue(SHORT_DESCRIPTION, "Import data from file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        this.importService = importService;
        this.refreshCallback = refreshCallback;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ImportDialog dialog = new ImportDialog(null, (filepath, importStrategy) -> {
            AsyncImportService asyncImportService = new AsyncImportService(importService, filepath, importStrategy, refreshCallback);
            asyncImportService.execute();
        });

        dialog.setVisible(true);
    }
}
