package cz.muni.fi.pv168.project.business.service.export.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.muni.fi.pv168.project.business.model.Unit;

import java.io.IOException;

public class UnitDeserializer extends JsonDeserializer<Unit> {
    public UnitDeserializer() {
    }

    @Override
    public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String guid = node.get("guid").asText();
        String name = node.get("name").asText();
        float conversionRatio = node.get("conversionRatio").floatValue();
        String abbreviation = node.get("abbreviation").asText();
        Unit conversionUnit = node.get("conversionUnit").isNull() ? null : new UnitDeserializer().deserialize(node.get("conversionUnit").traverse(jsonParser.getCodec()), deserializationContext);
        return new Unit(guid, name, conversionUnit, conversionRatio, abbreviation);
    }
}
