package cz.muni.fi.pv168.project.business.service.export.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.export.serializers.IngredientSerializer;

import java.io.IOException;

public class IngredientDeserializer extends JsonDeserializer<Ingredient> {
    public IngredientDeserializer() {
    }
    @Override
    public Ingredient deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
//        int id = node.get("id").asInt();
        UnitDeserializer unitDeserializer = new UnitDeserializer();
        Unit unit = unitDeserializer.deserialize(node.get("unit").traverse(jsonParser.getCodec()), deserializationContext);
        int nutritionalValue = node.get("nutritionalValue").asInt();
        Ingredient ingredient = new Ingredient(name, nutritionalValue, unit);
        return ingredient;
    }
}
