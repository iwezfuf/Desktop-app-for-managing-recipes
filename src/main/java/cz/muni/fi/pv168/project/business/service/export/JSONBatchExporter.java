package cz.muni.fi.pv168.project.business.service.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class JSONBatchExporter implements BatchExporter {
    Format format = new Format("json", List.of("json", "JSON"));

    @Override
    public void exportBatch(Batch batch, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {

            for (var recipe : batch.recipes()) {
                System.out.println(">>>>>>>>>>>>>>>> " + recipe.getName());
                try (FileWriter recipeWriter = new FileWriter(filePath, true)) {
                    ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectWriter.writeValue(recipeWriter, recipe);
                }
            }

            for (var ingredient : batch.ingredients()) {
                try (FileWriter ingredientWriter = new FileWriter(filePath, true)) {
                    ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectWriter.writeValue(ingredientWriter, ingredient);
                }
            }

            for (var unit : batch.units()) {
                try (FileWriter unitWriter = new FileWriter(filePath, true)) {
                    ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
                    objectWriter.writeValue(unitWriter, unit);
                }
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
