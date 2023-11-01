package cz.muni.fi.pv168.project.business.service.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONBatchExporter implements BatchExporter {
    Format format = new Format("json", List.of("json", "JSON"));

    @Override
    public void exportBatch(Batch batch, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

            for (var recipe : batch.recipes()) {
                objectWriter.writeValue(fileWriter, recipe);
            }

            for (var ingredient : batch.ingredients()) {
                objectWriter.writeValue(fileWriter, ingredient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Format getFormat() {
        return format;
    }
}
