package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.initialization.dsl.ScriptHandler;
import org.gradle.api.tasks.GradleBuild;
import org.gradle.api.tasks.TaskCollection;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class ScannerTest {

    Scanner sut = new Scanner();
    private Project project;


    @BeforeEach
    void setUp() {
        project = ProjectBuilder.builder().build();
        project.getPlugins().apply("java");
    }

    @Test
    void testDependenciesEmpty() {
        List<Artifact> artifacts = sut.extractDependencies(project);
        Assertions.assertEquals(0, artifacts.size());
    }

    @Test
    void testDependenciesOneCompile() {
        project.getDependencies().add("compile", "commons-lang:commons-lang:2.6");
        List<Artifact> artifacts = sut.extractDependencies(project);
        Assertions.assertEquals(1, artifacts.size());
    }

    @Test
    void testDependenciesExample2() {
        project.getDependencies().add("testImplementation", "org.junit.jupiter:junit-jupiter:5.6.2");
        project.getDependencies().add("testImplementation", "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0");

        List<Artifact> artifacts = sut.extractDependencies(project);
        Assertions.assertEquals(2, artifacts.size());
    }

    @Test
    void testDependenciesExample2Ignore() {
        project.getDependencies().add("testImplementation", "org.junit.jupiter:unspecified:5.6.2");
        project.getDependencies().add("testImplementation", "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0");

        List<Artifact> artifacts = sut.extractDependencies(project);
        Assertions.assertEquals(1, artifacts.size());
    }

    @Test
    void testDependenciesExample3SpringBoot() {
        // GIVEN

        Project project = ProjectBuilder.builder()
                .build();
        ScriptHandler buildscript = project.getBuildscript();
        Action<? super MavenArtifactRepository> action = new Action<MavenArtifactRepository>() {
            @Override
            public void execute(MavenArtifactRepository mavenArtifactRepository) {
                mavenArtifactRepository.setUrl("https://plugins.gradle.org/m2/");
            }
        };
        buildscript.getRepositories().maven(action);

        buildscript.getDependencies().add("classpath", "io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE");
        Map<Project, Set<Task>> allTasks = project.getAllTasks(true);
        project.getPlugins().apply("java");
        project.getDependencies().add("implementation", "com.fasterxml.jackson.core:jackson-annotations");
        project.getDependencies().add("implementation", "com.fasterxml.jackson.core:jackson:2.0.0");
        project.getPlugins().apply("io.spring.dependency-management");
        project.getState();
        // WHEN
        List<Artifact> artifacts = sut.extractDependencies(project);

        // THEN
        Assertions.assertEquals(1, artifacts.size());
        Assertions.assertEquals("d", artifacts.get(0).getVersion());
    }

    @Test
    public void testFo() {
        Task clean = project.getTasksByName("dependencies",false).iterator().next();
        Task foo = clean;
        clean.getActions().get(0).execute(foo);
        System.out.println(clean);
    }

}
