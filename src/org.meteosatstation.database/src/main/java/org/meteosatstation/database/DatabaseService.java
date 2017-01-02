package org.meteosatstation.database;

public interface DatabaseService {
    /**
     * Check that the database is available
     * @return bool
     */
    public boolean isDatabaseAvailable();
}
