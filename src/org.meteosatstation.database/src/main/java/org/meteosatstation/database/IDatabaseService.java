package org.meteosatstation.database;

import org.osgi.service.log.LogService;

public class IDatabaseService implements DatabaseService {
    private LogService logService;

    public IDatabaseService(LogService logService) {
        this.logService = logService;
    }

    public boolean isDatabaseAvailable() {
        return true;
    }
}
