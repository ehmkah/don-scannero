package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Project;

public class ValueReader {

    DonScanneroConfiguration readValues(Project project) {
        String projectVersion = readVersionOrFail(project);
        String group = readGroupIdOrFail(project);
        String projectName = readProjectName(project);
        return new DonScanneroConfiguration(projectName, group, projectVersion);
    }

    private String readProjectName(Project project) {
        String projectName = project.getName();
        return projectName;
    }

    private String readGroupIdOrFail(Project project) {
        String projectGroupId = project.getGroup().toString();
        if(projectGroupId == null|| "".equals(projectGroupId)) {
            throw new IllegalArgumentException("group for project is not set. please provide one in `gradle.properties` (see https://github.com/ehmkah/don-scannero/tree/master/poc_dependency_gradle_plugin_consumer)");
        }
        return projectGroupId;
    }

    private String readVersionOrFail(Project project) {
        String projectVersion = project.getVersion().toString();
        if (projectVersion == null||"unspecified".equals(projectVersion)) {
            throw new IllegalArgumentException("Version for project is not set. please provide one in `gradle.properties` (see https://github.com/ehmkah/don-scannero/tree/master/poc_dependency_gradle_plugin_consumer)");
        }
        return projectVersion;
    }
}
