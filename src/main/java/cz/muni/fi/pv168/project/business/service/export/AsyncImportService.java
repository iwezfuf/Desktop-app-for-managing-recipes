package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;

import javax.swing.*;

/**
 * @author Marek Eibel
 */
public class AsyncImportService extends SwingWorker<Void, Void> {

    private final ImportService importService;
    private final String filePath;
    private final ImportStrategy importStrategy;
    private final Runnable refreshCallBack;

    public AsyncImportService(ImportService importService, String filePath, ImportStrategy importStrategy, Runnable refreshCallBack)
    {
        this.importService = importService;
        this.filePath = filePath;
        this.importStrategy = importStrategy;
        this.refreshCallBack = refreshCallBack;
    }

    @Override
    protected Void doInBackground() throws BatchOperationException, NullPointerException, DataManipulationException {
        importService.importData(filePath, importStrategy);
        return null;
    }

    @Override
    protected void done() {

        try {
            get();
            refreshCallBack.run();
            JOptionPane.showMessageDialog(null, "Import was done");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while importing from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getFilePath() {
        return filePath;
    }
}
