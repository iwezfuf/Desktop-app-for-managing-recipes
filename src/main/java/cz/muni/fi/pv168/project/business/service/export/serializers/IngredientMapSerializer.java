package cz.muni.fi.pv168.project.business.service.export.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;

import java.io.IOException;
import java.util.Map;

public class IngredientMapSerializer extends JsonSerializer<Map<Ingredient, Integer>> {
    @Override
    public void serialize(
            Map<Ingredient, Integer> ingredients,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartArray();

        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("ingredient", entry.getKey());
            jsonGenerator.writeNumberField("quantity", entry.getValue());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();
    }
}