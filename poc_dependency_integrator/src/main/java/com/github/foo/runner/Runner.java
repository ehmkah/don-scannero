package com.github.foo.runner;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

public class Runner {

    public void run(String projectPath) {
        ProjectConnection projectConnection = GradleConnector.newConnector()
                .forProjectDirectory(new File(projectPath))
                .connect();
        projectConnection.newBuild().forTasks("clean").run();
        projectConnection.newBuild().forTasks("scannero").run();
    }
}
