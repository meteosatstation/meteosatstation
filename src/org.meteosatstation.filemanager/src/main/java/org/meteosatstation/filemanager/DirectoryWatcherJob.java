package org.meteosatstation.filemanager;

import org.osgi.service.log.LogService;
import java.util.TimerTask;

public class DirectoryWatcherJob extends TimerTask {
    private LogService logService;
    private Config config;

    public DirectoryWatcherJob(LogService logService, Config config) {
        this.logService = logService;
        this.config = config;
    }

    public void run() {
        logService.log(LogService.LOG_INFO, "Job run");
    }
}
