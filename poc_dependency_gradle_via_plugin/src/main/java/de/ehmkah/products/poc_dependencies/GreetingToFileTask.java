package de.ehmkah.products.poc_dependencies;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

class GreetingToFileTask extends DefaultTask {

    @Inject
    public GreetingToFileTask() {
    }

    @Input
    String groupID = "<groupIdShouldBeSet/>";

    @Input
    String artifactId = "<artifactIdShouldBeSet/>";

    @Input
    String version = "<versionShouldBeSet/>";

    @TaskAction
    public void scan() {
        System.out.println("groupID:" + groupID);
        System.out.println("artifactId:" + artifactId);
        System.out.println("version:" + version);

    }
}