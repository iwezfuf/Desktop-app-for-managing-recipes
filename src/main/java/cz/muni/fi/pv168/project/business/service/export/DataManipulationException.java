package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.error.RuntimeApplicationException;

/**
 * Exception thrown if there is some problem with data import/export.
 */
public final class DataManipulationException extends RuntimeApplicationException {

    public DataManipulationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataManipulationException(String message) {
        super(message);
    }
}
