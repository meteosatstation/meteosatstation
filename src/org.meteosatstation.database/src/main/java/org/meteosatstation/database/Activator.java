package org.meteosatstation.database;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Hashtable;

public class Activator implements BundleActivator {
    public void start(BundleContext bundleContext)
    {
        ServiceTracker logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
        logServiceTracker.open();
        LogService logService = (LogService) logServiceTracker.getService();

        Hashtable<String, String> serviceProperties = new Hashtable<String, String>();

        bundleContext.registerService(
                DatabaseService.class.getName(), new IDatabaseService(logService), serviceProperties);
    }

    public void stop(BundleContext bundleContext)
    {
        // NOTE: The service is automatically unregistered.
    }
}
