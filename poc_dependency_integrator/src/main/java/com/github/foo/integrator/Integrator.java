package com.github.foo.integrator;

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
        List<String> gradleFileContents = Files.readAllLines(Paths.get(inputFile.toURI()));
        if (integrationNeeded()) {
            GradleDependencyUpdater dependencyUpdater = new GradleDependencyUpdater(gradleFileContents);
            dependencyUpdater.insertDependency();

            GradleApplyUpdater applyUpdater = new GradleApplyUpdater(dependencyUpdater.getGradleFileContents());
            applyUpdater.insertApply();

            GradleRepositoryUpdater repositoryUpdater = new GradleRepositoryUpdater(applyUpdater.getGradleFileContents());
            repositoryUpdater.insertApply();

            return applyUpdater.getGradleFileContents();
        } else {
            return gradleFileContents;
        }
    }

    private boolean integrationNeeded() {
        return true;
    }
}
