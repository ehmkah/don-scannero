package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DonScanneroPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("scannero", DonScanneroPlugin.class);
        project.getTasks().create("scannero", DonScanneroTask.class);
    }
}

