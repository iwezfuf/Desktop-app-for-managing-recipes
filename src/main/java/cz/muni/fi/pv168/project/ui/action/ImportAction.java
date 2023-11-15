package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.ui.dialog.ImportDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
            importService.importData(filepath, importStrategy);
            refreshCallback.run();
            JOptionPane.showMessageDialog(frame, "Import was done");
        });
        dialog.setVisible(true);
    }
}
