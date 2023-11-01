package cz.muni.fi.pv168.project.business.service.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONBatchImporter implements BatchImporter {
    Format format = new Format("json", List.of("json", "JSON"));

    @Override
    public Batch importBatch(String filePath) {
        Batch batch = new Batch(new ArrayList<>(), new ArrayList<>());

        try {
            File file = new File(filePath);

            FileReader fileReader = new FileReader(file);
            ObjectMapper objectMapper = new ObjectMapper();

            Recipe[] recipes = objectMapper.readValue(fileReader, Recipe[].class);
            for (var recipe : recipes) {
                batch.recipes().add(recipe);
            }

            Ingredient[] ingredients = objectMapper.readValue(fileReader, Ingredient[].class);
            for (var ingredient : ingredients) {
                batch.ingredients().add(ingredient);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batch;
    }


    @Override
    public Format getFormat() {
        return format;
    }
}
