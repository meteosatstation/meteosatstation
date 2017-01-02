package org.meteosatstation.format.hrit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import org.meteosatstation.format.formatinterface.FormatInterface;

import java.util.Hashtable;

public class Activator implements BundleActivator {
    public HRITDecoder hritDecoder;

    public void start(BundleContext bundleContext) {
        // setup logging
        ServiceTracker logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
        logServiceTracker.open();
        LogService logService = (LogService) logServiceTracker.getService();

        // set the properties of this service
        Hashtable<String, String> serviceProperties = new Hashtable<String, String>();
        serviceProperties.put(Constants.SERVICE_PID, "org.meteosatstation.format.hrit");

        // create instance of the IFileManager
        this.hritDecoder = new HRITDecoder(logService);

        // register the main service
        bundleContext.registerService(
                FormatInterface.class.getName(), hritDecoder, serviceProperties);
    }

    public void stop(BundleContext bundleContext) {
        System.out.println("Stopping the bundle");
    }

}