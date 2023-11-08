package cz.muni.fi.pv168.project.business.service.export.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;

import java.io.IOException;

public class RecipeIngredientAmountDeserializer extends JsonDeserializer<RecipeIngredientAmount> {
    public RecipeIngredientAmountDeserializer() {
    }
    @Override
    public RecipeIngredientAmount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        IngredientDeserializer ingredientDeserializer = new IngredientDeserializer();
        int amount = node.get("amount").asInt();
        return new RecipeIngredientAmount(ingredientDeserializer.deserialize(node.get("ingredient").traverse(jsonParser.getCodec()), deserializationContext), amount);
    }
}
