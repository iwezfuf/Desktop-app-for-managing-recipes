package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;

public final class TestDependencyProvider extends CommonDependencyProvider {

    public TestDependencyProvider(DatabaseManager databaseManager) {
        super(databaseManager);
    }
}
