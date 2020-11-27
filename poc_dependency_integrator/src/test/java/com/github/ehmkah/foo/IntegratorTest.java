package com.github.ehmkah.foo;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.junit.jupiter.api.Test;

import java.io.File;

public class IntegratorTest {

    @Test
    public void testFoo() {
        ProjectConnection projectConnection = GradleConnector.newConnector()
                .forProjectDirectory(new File("../poc_dependency_gradle_plugin_consumer"))
                .connect();
        projectConnection.newBuild().forTasks("clean").run();
        projectConnection.newBuild().forTasks("scannero").run();
    }
}
