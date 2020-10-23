package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Project;
import org.gradle.internal.impldep.org.junit.Assert;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

public class ScannerPluginTest {

    @Test
    public void testPluginApplied() {
        // GIVEN
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("de.ehmkah.projects.poc_dependencies");
        project.getTasks().create("scanner", DonScanneroTask.class);
        // WHEN
        // THEN
        Assert.assertNotNull(project.getTasks().findByName("scanner"));
    }

    @Test
    public void testPluginNotApplied() {
        // GIVEN
        Project project = ProjectBuilder.builder().build();
        // WHEN
        // THEN
        Assert.assertNull(project.getTasks().findByName("scanner"));
    }


}
