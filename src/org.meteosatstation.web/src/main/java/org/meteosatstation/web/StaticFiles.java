package org.meteosatstation.web;

import spark.Spark;
import spark.servlet.SparkApplication;

public class StaticFiles implements SparkApplication {
    public void init() {
        // set the static file location
        Spark.staticFileLocation("/public");
    }
}
