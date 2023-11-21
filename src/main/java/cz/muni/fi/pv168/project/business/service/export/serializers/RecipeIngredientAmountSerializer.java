package cz.muni.fi.pv168.project.business.service.export.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;

import java.io.IOException;

public class RecipeIngredientAmountSerializer extends JsonSerializer<RecipeIngredientAmount> {
    public RecipeIngredientAmountSerializer() {
    }
    @Override
    public void serialize(RecipeIngredientAmount recipeIngredientAmount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("guid", recipeIngredientAmount.getGuid());
//        jsonGenerator.writeObjectField("recipe", recipeIngredientAmount.getRecipe());
        jsonGenerator.writeObjectField("ingredient", recipeIngredientAmount.getIngredient());
        jsonGenerator.writeNumberField("amount", recipeIngredientAmount.getAmount());
        jsonGenerator.writeEndObject();
    }
}
