package org.meteosatstation.filemanager;

import org.meteosatstation.database.DatabaseService;
import org.meteosatstation.format.formatinterface.FormatInterface;

import org.osgi.framework.*;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import java.util.ArrayList;
import java.util.Hashtable;

public class Activator implements BundleActivator {
    private IFileManager fileManager;
    private DatabaseService databaseService;
    private ArrayList<FormatInterface> formatInterfaces;

    public void start(BundleContext bundleContext) {
        // setup logging
        ServiceTracker logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
        logServiceTracker.open();
        LogService logService = (LogService) logServiceTracker.getService();

        // see if the database service is available
        ServiceReference databaseServiceReference = bundleContext.getServiceReference(DatabaseService.class.getName());

        try {
            this.databaseService = (DatabaseService) bundleContext.getService(databaseServiceReference);

            if (this.databaseService.isDatabaseAvailable() == true) {
                logService.log(LogService.LOG_INFO, "Database is available...");
            } else {
                logService.log(LogService.LOG_WARNING, "Unable to get the database service");
            }
        } catch (Exception e) {
            logService.log(LogService.LOG_WARNING, "Unable to get the database service");
        }

        try {
            ServiceReference<?>[] formatServices = bundleContext.getServiceReferences(FormatInterface.class.getName(),
                    null);

            logService.log(LogService.LOG_INFO, "Discovered " + formatServices.length + " decoding services");

            for (int i = 0; i <= formatServices.length; i++) {
                logService.log(LogService.LOG_INFO,
                        "Loading format: " + bundleContext.getService(formatServices[i]).getClass().getName());

                formatInterfaces.add((FormatInterface)bundleContext.getService(formatServices[i]));
            }
        } catch (InvalidSyntaxException e) {
            logService.log(LogService.LOG_ERROR, "Unable to load the decoding services: " + e.getMessage());
        }


        // set the properties of this service
        Hashtable<String, String> serviceProperties = new Hashtable<String, String>();
        serviceProperties.put(Constants.SERVICE_PID, "org.meteosatstation.filemanager");

        // create instance of the IFileManager
        this.fileManager = new IFileManager(logService);

        // register the main service
        bundleContext.registerService(
                FileManager.class.getName(), fileManager, serviceProperties);

        // register the managed service
        bundleContext.registerService(
                ManagedService.class.getName(), fileManager, serviceProperties);

    }

    public void stop(BundleContext bundleContext) {
        this.fileManager.stopFileManager();
    }

}