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
    public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
//        int id = node.get("id").asInt();
        int conversionRatio = node.get("conversionRatio").asInt();
        String abbreviation = node.get("abbreviation").asText();
        Unit conversionUnit = new Unit();
        Unit result = new Unit(name, conversionUnit, conversionRatio, abbreviation);
        //Unit.getListOfUnits().remove(conversionUnit);
        //Unit.getListOfUnits().remove(result);
        return result;
    }
}
