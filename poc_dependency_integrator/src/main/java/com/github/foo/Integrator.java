package com.github.foo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Does the work - take a build.gradle and add all required information so don scannero can run.
 */
public class Integrator {

    public List<String> integrate(final File inputFile) throws IOException {
        GradleDependencyUpdater updater = new GradleDependencyUpdater(inputFile);

        FindDependenciesVisitor visitor = updater.insertDependency(
                "        classpath \"com.github.ehmkah:don-scannero:0.0.7-SNAPSHOT\"");

        return updater.getGradleFileContents();
    }
}
