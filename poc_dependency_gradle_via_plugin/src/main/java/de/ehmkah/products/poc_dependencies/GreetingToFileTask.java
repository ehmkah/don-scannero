package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
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
import java.util.List;

class GreetingToFileTask extends DefaultTask {

    private Neo4Repository neo4Repository;

    private Scanner scanner;

    @Inject
    public GreetingToFileTask() {
        neo4Repository = new Neo4Repository("bolt://localhost:7687", "neo4j", "password");
        scanner = new Scanner();
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
        Project project = getProject();
        Artifact basis = new Artifact(artifactId, groupID,version);
        neo4Repository.writeArtifact(basis);
        List<Artifact> artifacts = scanner.extractDependencies(project);
        neo4Repository.writeArtifacts(basis, artifacts);


    }
}