package cz.muni.fi.pv168.project.business.service.export.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;

import java.io.IOException;

public class IngredientDeserializer extends JsonDeserializer<Ingredient> {
    public IngredientDeserializer() {
    }

    @Override
    public Ingredient deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String guid = node.get("guid").asText();
        String name = node.get("name").asText();
        UnitDeserializer unitDeserializer = new UnitDeserializer();
        Unit unit = unitDeserializer.deserialize(node.get("unit").traverse(jsonParser.getCodec()), deserializationContext);
        int nutritionalValue = node.get("nutritionalValue").asInt();
        return new Ingredient(guid, name, nutritionalValue, unit);
    }
}
