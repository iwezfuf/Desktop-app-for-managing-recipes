package cz.muni.fi.pv168.project.storage.sql.dao;

/**
 * Checked exception that is used when there is an invalid deletion in the repository.
 *
 * @author Marek Eibel
 */
public class InvalidDataDeletionException extends Exception {

    public InvalidDataDeletionException() {
        super();
    }

    public InvalidDataDeletionException(String message) {
        super(message);
    }

    public InvalidDataDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
