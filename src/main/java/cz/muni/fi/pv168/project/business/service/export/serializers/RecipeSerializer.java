package cz.muni.fi.pv168.project.business.service.export.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cz.muni.fi.pv168.project.business.model.Recipe;

import java.io.IOException;

public class RecipeSerializer extends JsonSerializer<Recipe> {
    public RecipeSerializer() {
    }
    @Override
    public void serialize(Recipe recipe, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", recipe.getName());
        jsonGenerator.writeStringField("description", recipe.getDescription());
        jsonGenerator.writeNumberField("id", recipe.getRecipeId());
        jsonGenerator.writeNumberField("preparationTime", recipe.getPreparationTime());
        jsonGenerator.writeNumberField("numOfServings", recipe.getNumOfServings());
        jsonGenerator.writeStringField("instructions", recipe.getInstructions());
        jsonGenerator.writeObjectField("category", recipe.getCategory());
        jsonGenerator.writeObjectField("ingredients", recipe.getIngredients());
        jsonGenerator.writeEndObject();
    }
}