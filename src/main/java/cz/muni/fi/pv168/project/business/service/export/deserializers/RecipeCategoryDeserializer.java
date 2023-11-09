package cz.muni.fi.pv168.project.business.service.export.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.io.IOException;

public class RecipeCategoryDeserializer extends JsonDeserializer<RecipeCategory> {
    public RecipeCategoryDeserializer() {
    }
    @Override
    public RecipeCategory deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
//        int id = node.get("id").asInt();
        Color color = new Color(node.get("color").asInt());
        return new RecipeCategory(name, color);
    }
}
