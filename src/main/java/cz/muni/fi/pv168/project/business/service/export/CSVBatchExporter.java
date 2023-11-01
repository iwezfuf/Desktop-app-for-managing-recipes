package cz.muni.fi.pv168.project.business.service.export;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVBatchExporter implements BatchExporter {
    Format format = new Format("CSV", List.of("csv", "CSV", "Csv"));

    @Override
    public void exportBatch(Batch batch, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath);
             ICSVWriter csvWriter = new CSVWriterBuilder(fileWriter).withSeparator(',').build()) {

            for (Ingredient ingredient : batch.ingredients()) {
                String[] ingData = {ingredient.getGuid(), String.valueOf(ingredient.getName()), String.valueOf(ingredient.getUnit()), String.valueOf(ingredient.getNutritionalValue())};
                csvWriter.writeNext(ingData);
            }

            csvWriter.writeNext(new String[]{System.lineSeparator()});

            for (var recipe : batch.recipes()) {
                String[] recData = {recipe.getGuid(), String.valueOf(recipe.getName()), String.valueOf(recipe.getIngredients()), String.valueOf(recipe.getInstructions())};
                csvWriter.writeNext(recData);
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
