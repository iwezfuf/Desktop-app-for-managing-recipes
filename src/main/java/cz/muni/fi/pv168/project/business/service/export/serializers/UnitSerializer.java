package cz.muni.fi.pv168.project.business.service.export.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cz.muni.fi.pv168.project.business.model.Unit;

import java.io.IOException;

public class UnitSerializer extends JsonSerializer<Unit> {
    public UnitSerializer() {
    }

    @Override
    public void serialize(Unit unit, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("guid", unit.getGuid());
        jsonGenerator.writeStringField("name", unit.getName());
        jsonGenerator.writeNumberField("conversionRatio", unit.getConversionRatio());
        jsonGenerator.writeStringField("abbreviation", unit.getAbbreviation());
        jsonGenerator.writeObjectField("conversionUnit", unit.getConversionUnit());
        jsonGenerator.writeEndObject();
    }
}
