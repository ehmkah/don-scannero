package com.github.ehmkah.foo;

import com.github.foo.FindDependenciesVisitor;
import com.github.foo.GradleDependencyUpdater;
import com.github.foo.Integrator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegratorTest {

    private Integrator sut = new Integrator();

    @Test
    public void testExampl1() throws IOException {
        final File inputFile = new File("src/test/resources/given.example1.gradle");
        final File outputFile = new File("src/test/resources/expected.example1.gradle");

        GradleDependencyUpdater updater = new GradleDependencyUpdater(inputFile);
        GradleDependencyUpdater updater2 = new GradleDependencyUpdater(outputFile);

        FindDependenciesVisitor visitor = updater.insertDependency(
                "compile \"com.github.ehmkah.don-scannero1.0.0\"");

        //int dependenceLineNum = visitor.getDependenceLineNum();

        //assertEquals(20, dependenceLineNum);

        List<String> actualFileContent = updater.getGradleFileContents();
        List<String> expectedFileContent = updater2.getGradleFileContents();

        assertEquals(expectedFileContent, actualFileContent);
    }
}
