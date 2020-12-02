package com.github.foo;

import com.github.foo.integrator.Integrator;
import com.github.foo.runner.Runner;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Runs for given project the integrator and the runer.
 */
public class Application {

    private Integrator integrator = new Integrator();
    private Runner runner = new Runner();

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("path to directory where build.gradle lives, as first parameter please");
        }
        String projectPath = args[0];
        Application application = new Application();
        application.run(projectPath);
    }

    public void run(String projectPath) throws IOException {
        File buildFile = new File(projectPath);
        List<String> newgradleFile = integrator.integrate(buildFile);

        IOUtils.write(String.join("\n", newgradleFile), new FileOutputStream(buildFile), StandardCharsets.UTF_8);
        runner.run(projectPath);
    }
}
