package cz.muni.fi.pv168.project.business.service.export.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;

import java.io.IOException;

public class IngredientSerializer extends JsonSerializer<Ingredient> {
    public IngredientSerializer() {
    }

    @Override
    public void serialize(Ingredient ingredient, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("guid", ingredient.getGuid());
        jsonGenerator.writeStringField("name", ingredient.getName());
        jsonGenerator.writeObjectField("unit", ingredient.getUnit());
        jsonGenerator.writeNumberField("nutritionalValue", ingredient.getNutritionalValue());
        jsonGenerator.writeEndObject();
    }
}
