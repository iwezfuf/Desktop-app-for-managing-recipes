package cz.muni.fi.pv168.project.storage.sql.entity;

public record UnitEntity (Long id, String guid, String name, String abbreviation, float conversionRatio, long conversionUnitId) {
    public UnitEntity(Long id, String guid, String name, String abbreviation, float conversionRatio, long conversionUnitId) {
        this.id = id;
        this.guid = guid;
        this.name = name;
        this.abbreviation = abbreviation;
        this.conversionRatio = conversionRatio;
        this.conversionUnitId = conversionUnitId;
    }

    public UnitEntity(String guid, String name, String abbreviation, float conversionRatio, long conversionUnitId) {
        this(null, guid, name, abbreviation, conversionRatio, conversionUnitId);
    }
}
