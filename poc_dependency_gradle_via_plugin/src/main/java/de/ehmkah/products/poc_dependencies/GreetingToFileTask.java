package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.writer.Neo4Repository;
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

    private Neo4Repository neo4Repository;

    @Inject
    public GreetingToFileTask() {
        neo4Repository = new Neo4Repository("bolt://localhost:7687", "neo4j", "password");
    }

    @Input
    String groupID = "dummyGroupId";

    @Input
    String artifactId = "dummyArtifactId";

    @Input
    String version = "dummyVersion";

    @TaskAction
    public void scan() {
        System.out.println("groupID:" + groupID);
        System.out.println("artifactId:" + artifactId);
        System.out.println("version:" + version);
        neo4Repository.writeArtifact(groupID, artifactId, version);
        Project project = getProject();
        ConfigurationContainer configurations = project.getConfigurations();
        for (Configuration configuration : configurations) {
            DependencySet dependencies = configuration.getDependencies();
            for (Dependency dependency : dependencies) {
                neo4Repository.writeDependency(groupID, artifactId, version, dependency);
            }

        }

    }
}