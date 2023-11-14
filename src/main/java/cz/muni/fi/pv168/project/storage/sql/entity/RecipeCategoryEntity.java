package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.Gender;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of Department entity in a SQL database.
 */
public record RecipeCategoryEntity(Long id, String guid, String name, int color) {
    public RecipeCategoryEntity(Long id, String guid, String name, int color) {
        this.id = id;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.color = Objects.requireNonNull(color, "color must not be null");

    }

    public RecipeCategoryEntity(
            String guid, String name, int color) {
        this(null, guid, name, color);

    }
}
