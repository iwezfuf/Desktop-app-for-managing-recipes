package cz.muni.fi.pv168.project.business.service.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.deserializers.IngredientDeserializer;
import cz.muni.fi.pv168.project.business.service.export.deserializers.RecipeCategoryDeserializer;
import cz.muni.fi.pv168.project.business.service.export.deserializers.RecipeDeserializer;
import cz.muni.fi.pv168.project.business.service.export.deserializers.RecipeIngredientAmountDeserializer;
import cz.muni.fi.pv168.project.business.service.export.deserializers.UnitDeserializer;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONBatchImporter implements BatchImporter {
    Format format = new Format("json", List.of("json", "JSON"));

    @Override
    public Batch importBatch(String filePath) {
        Batch batch;
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Recipe.class, new RecipeDeserializer());
        module.addDeserializer(Ingredient.class, new IngredientDeserializer());
        module.addDeserializer(Unit.class, new UnitDeserializer());
        module.addDeserializer(RecipeCategory.class, new RecipeCategoryDeserializer());
        module.addDeserializer(RecipeIngredientAmount.class, new RecipeIngredientAmountDeserializer());
        objectMapper.registerModule(module);

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            batch = objectMapper.readValue(jsonString, Batch.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return batch;
    }

    @Override
    public Format getFormat() {
        return format;
    }
}
