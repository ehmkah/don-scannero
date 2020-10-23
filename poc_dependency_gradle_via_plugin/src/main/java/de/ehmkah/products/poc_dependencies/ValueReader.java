package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Project;

public class ValueReader {

    DonScanneroConfiguration readValues(Project project) {
        String projectVersion = project.getVersion().toString();
        if (projectVersion == null||projectVersion.equals("unspecified")) {
            throw new IllegalArgumentException("Version for project is not set. please provide one in `gradle.properties` (see https://github.com/ehmkah/don-scannero/tree/master/poc_dependency_gradle_plugin_consumer)");
        }

        return new DonScanneroConfiguration(projectVersion);
    }
}
