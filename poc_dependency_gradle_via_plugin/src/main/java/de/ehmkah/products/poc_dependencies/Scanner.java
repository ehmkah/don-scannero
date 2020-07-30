package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;

import java.util.LinkedList;
import java.util.List;

public class Scanner {

    public List<Artifact> extractDependencies(Project project) {
        List<Artifact> result = new LinkedList<>();

        ConfigurationContainer configurations = project.getConfigurations();
        for (Configuration configuration : configurations) {
            DependencySet dependencies = configuration.getDependencies();
            for (Dependency dependency : dependencies) {
                result.add(new Artifact(dependency.getName(), dependency.getGroup(), dependency.getVersion()));
            }
        }

        return result;
    }
}
