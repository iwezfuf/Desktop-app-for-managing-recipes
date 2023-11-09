package cz.muni.fi.pv168.project.business.model;

public abstract class Entity {
    /**
     * Finds globally unique identifier of the given entity.
     */
    public abstract String getGuid();
}
