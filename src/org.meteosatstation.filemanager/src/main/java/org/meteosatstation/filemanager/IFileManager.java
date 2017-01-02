package org.meteosatstation.filemanager;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;

import java.util.Dictionary;
import java.util.Timer;

public class IFileManager implements FileManager, ManagedService {
    /**
     * Holds an instance of the LogService
     */
    private LogService logService;

    private Config config;

    private Timer jobTimer;
    private DirectoryWatcherJob directoryWatcherJob;

    private boolean loopIsRunning;

    public IFileManager(LogService logService) {
        // Copy the logService instance to the class for use by everything
        this.logService = logService;

        // create the config object
        this.config = new Config();

        // create an instance of our job
        this.directoryWatcherJob = new DirectoryWatcherJob(this.logService, this.config);

        // create a timer
        this.jobTimer = new Timer();

        if (this.config.isConfigured()) {
            startFileManager();
        } else {
            logService.log(LogService.LOG_INFO, "We cannot start until we have received configuration");
        }
    }

    public void updated(Dictionary config) throws ConfigurationException {
        logService.log(LogService.LOG_INFO, "Updating configuration");

        if (config == null) {
            logService.log(LogService.LOG_ERROR, "There is no configuration for this module");
            return;
        } else {
            // Should we delete files after they have been imported?
            if (config.get("delete_files_after_import") == "yes") {
                this.config.deleteFilesAfterImport = true;
            } else if (config.get("delete_files_after_import") == "no") {
                this.config.deleteFilesAfterImport= false;
            } else {
                logService.log(LogService.LOG_WARNING, "Config Parameter 'delete_files_after_import' is not set, defaulting to 'no'");
                this.config.deleteFilesAfterImport = false;
            }

            // Should we delete files if we cannot decode them?
            if (config.get("delete_files_cannot_decode") == "yes") {
                this.config.deleteFilesIfCannotRead = true;
            } else if (config.get("delete_files_cannot_decode") == "no") {
                this.config.deleteFilesIfCannotRead = false;
            } else {
                logService.log(LogService.LOG_WARNING, "Config Parameter 'delete_files_cannot_decode' is not set, defaulting to 'no'");
                this.config.deleteFilesIfCannotRead = false;
            }

            // Get the directory of the directory that we need to scan
            if (config.get("file_dir").equals(null)) {
                logService.log(LogService.LOG_ERROR, "No file directory is set, we are unable to continue");
            }
        }

        // restart the main loop
        restartFileManager();
    }

    public void startFileManager() {
        // Print to the log
        logService.log(LogService.LOG_INFO, "Starting File Manager");

        // start the timer
        this.jobTimer.schedule(this.directoryWatcherJob, 0, 2000);

        // set the variable so we know the loop should be running
        this.loopIsRunning = true;

    }

    public void stopFileManager() {
        // print to the log
        logService.log(LogService.LOG_INFO, "Stopping File Manager");

        // stop the timer job
        this.jobTimer.cancel();
    }

    public void restartFileManager() {
        if (this.loopIsRunning == true) {
            stopFileManager();
        }

        startFileManager();
    }
}
