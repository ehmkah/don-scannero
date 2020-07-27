package de.ehmkah.products.poc_dependencies;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
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
        Project project = getProject();
        ConfigurationContainer configurations = project.getConfigurations();
        for (Configuration configuration : configurations) {
            DependencySet dependencies = configuration.getDependencies();
            for (Dependency dependency : dependencies) {
                System.out.println(dependency.getGroup() + ":" + dependency.getName() + ":" + dependency.getVersion());
            }

        }

    }
}