package com.github.foo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Does the work - take a build.gradle and add all required information so don scannero can run.
 */
public class Integrator {

    public List<String> integrate(final File inputFile) throws IOException {
        GradleDependencyUpdater dependencyUpdater = new GradleDependencyUpdater(Files.readAllLines(Paths.get(inputFile.toURI())));
        dependencyUpdater.insertDependency("        classpath \"com.github.ehmkah:don-scannero:0.0.7-SNAPSHOT\"");

        GradleApplyUpdater applyUpdater = new GradleApplyUpdater(dependencyUpdater.getGradleFileContents());
        applyUpdater.insertApply("apply plugin: \"com.github.ehmkah.don-scannero\"");

        GradleRepositoryUpdater repositoryUpdater = new GradleRepositoryUpdater(applyUpdater.getGradleFileContents());
        repositoryUpdater.insertApply();


        return applyUpdater.getGradleFileContents();
    }
}
