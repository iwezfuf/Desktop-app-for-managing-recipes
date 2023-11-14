package cz.muni.fi.pv168.project.storage.sql.entity;

public record IngredientEntity (Long id, String guid, String name, int nutritionalValue, long unitId) {
    public IngredientEntity(Long id, String guid, String name, int nutritionalValue, long unitId) {
        this.id = id;
        this.guid = guid;
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.unitId = unitId;
    }

    public IngredientEntity(String guid, String name, int nutritionalValue, long unitId) {
        this(null, guid, name, nutritionalValue, unitId);
    }
}
