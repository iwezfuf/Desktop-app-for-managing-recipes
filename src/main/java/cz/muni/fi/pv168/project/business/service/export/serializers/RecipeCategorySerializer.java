package cz.muni.fi.pv168.project.business.service.export.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;

import java.io.IOException;

public class RecipeCategorySerializer extends JsonSerializer<RecipeCategory> {
    public RecipeCategorySerializer() {
    }

    @Override
    public void serialize(RecipeCategory recipeCategory, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", recipeCategory.getName());
        jsonGenerator.writeNumberField("id", recipeCategory.getId());
        jsonGenerator.writeNumberField("color", recipeCategory.getColor().getRGB());
        jsonGenerator.writeEndObject();
    }

}
