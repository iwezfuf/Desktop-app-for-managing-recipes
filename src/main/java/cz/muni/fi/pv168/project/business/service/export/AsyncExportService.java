package cz.muni.fi.pv168.project.business.service.export;

import javax.swing.*;

/**
 * Generic asynchronous export service.
 *
 * @author Marek Eibel
 */
public class AsyncExportService extends SwingWorker<Void, Void> {

    private final ExportService exportService;
    private final String filePath;

    public AsyncExportService(ExportService exportService, String filePath) {
        this.exportService = exportService;
        this.filePath = filePath;
    }

    @Override
    protected Void doInBackground() {
        exportService.exportData(filePath);
        return null;
    }

    @Override
    protected void done() {
        JOptionPane.showMessageDialog(null, "Export has successfully finished.");
    }

    public String getFilePath() {
        return filePath;
    }
}
