package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.writer.Neo4Repository;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.internal.impldep.org.junit.Assert;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScannerPluginTest {

    private Neo4Repository neo4Repository = Mockito.mock(Neo4Repository.class);

    @Test
    public void testPlugin() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("de.ehmkah.projects.poc_dependencies");
        project.getPlugins().apply("java");
        project.getDependencies().add("compile", "commons-lang:commons-lang:2.6");
        project.getDependencies().add("compile", "com.google:guava:29.6");
        project.getTasks().create("scanner", GreetingToFileTask.class);
        Assert.assertNotNull(project.getTasks().findByName("scanner"));
        Task scannerTask = project.getTasks().findByName("scanner");
        scannerTask.getActions().get(0).execute(scannerTask);
    }


}
