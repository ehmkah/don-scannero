package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Project;
import org.gradle.internal.impldep.org.junit.Assert;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

public class ScannerPluginTest {

    @Test
    public void testPlugin() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("de.ehmkah.projects.poc_dependencies");

        Assert.assertNotNull(project.getTasks().findByName("scanner"));
    }


}
