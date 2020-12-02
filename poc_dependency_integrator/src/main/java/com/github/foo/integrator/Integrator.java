package com.github.foo.integrator;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Does the work - take a build.gradle and add all required information so don scannero can run.
 */
public class Integrator {

    /**
     * Starts integrating
     * @param args -
     *             [0] => name of the gradle file where don-scannero should be applied
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("path to build.gradle as first parameter needed");
        }
        Integrator integrator = new Integrator();
        File buildFile = new File(args[0]);
        List<String> newgradleFile = integrator.integrate(buildFile);

        IOUtils.write(String.join("\n", newgradleFile), new FileOutputStream(buildFile), StandardCharsets.UTF_8);
    }

    public List<String> integrate(final File inputFile) throws IOException {
        GradleDependencyUpdater dependencyUpdater = new GradleDependencyUpdater(Files.readAllLines(Paths.get(inputFile.toURI())));
        dependencyUpdater.insertDependency();

        GradleApplyUpdater applyUpdater = new GradleApplyUpdater(dependencyUpdater.getGradleFileContents());
        applyUpdater.insertApply();

        GradleRepositoryUpdater repositoryUpdater = new GradleRepositoryUpdater(applyUpdater.getGradleFileContents());
        repositoryUpdater.insertApply();

        return applyUpdater.getGradleFileContents();
    }
}
