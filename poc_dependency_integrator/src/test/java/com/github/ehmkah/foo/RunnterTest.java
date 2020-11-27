package com.github.ehmkah.foo;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.GradleProject;
import org.junit.jupiter.api.Test;

import java.io.File;

// Maybe for running the builds?
public class RunnterTest {

    @Test
    public void testFoo() {
        ProjectConnection projectConnection = GradleConnector.newConnector()
                .forProjectDirectory(new File("../poc_dependency_gradle_plugin_consumer"))
                .connect();
        GradleProject project = projectConnection.getModel(GradleProject.class);
        projectConnection.newBuild().forTasks("clean").run();
        projectConnection.newBuild().forTasks("scannero").run();
    }
}
