package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        project.getBuildscript().getRepositories().mavenCentral();
        project.getBuildscript().getDependencies().add("classpath", "io.spring.gradle:dependency-management-plusgin:0.5.3.RELEASE");
        project.getPluginManager().apply("io.spring.dependency-management");
        project.getDependencies().add("implementation", "org.springframework.boot:spring-boot-starter-web");
        project.getDependencies().add("testImplementation", "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0");

        List<Artifact> artifacts = sut.extractDependencies(project);
        Assertions.assertEquals(2, artifacts.size());
    }

}