package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class ScannerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("scannero", ScannerPlugin.class);
    }
}

