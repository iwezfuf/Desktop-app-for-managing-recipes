package cz.muni.fi.pv168.project.storage.sql.entity;

import java.util.Objects;

/**
 * Representation of Department entity in a SQL database.
 */
public record RecipeEntity(Long id, String guid, String name, String description, int preparationTime, int numOfServings, String instructions, long recipeCategoryId) {
    public RecipeEntity(Long id, String guid, String name, String description, int preparationTime, int numOfServings, String instructions, long recipeCategoryId) {
        this.id = id;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.preparationTime = Objects.requireNonNull(preparationTime, "preparationTime must not be null");
        this.numOfServings = Objects.requireNonNull(numOfServings, "numOfServings must not be null");
        this.instructions = Objects.requireNonNull(instructions, "instructions must not be null");
        this.recipeCategoryId = Objects.requireNonNull(recipeCategoryId, "recipeCategoryId must not be null");
    }

    public RecipeEntity(
            String guid, String name, String description,
            int preparationTime, int numOfServings, String instructions,
            long recipeCategoryId) {
        this(null, guid, name, description, preparationTime, numOfServings, instructions, recipeCategoryId);
    }
}
