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
        // GIVEN
        final File inputFile = new File("src/test/resources/given.example1.gradle");
        final File expectedFile = new File("src/test/resources/expected.example1.gradle");
        GradleDependencyUpdater updater2 = new GradleDependencyUpdater(expectedFile);
        List<String> expectedFileContent = updater2.getGradleFileContents();

        // WHEN
        List<String> actualFileContent = sut.integrate(inputFile);

        // THEN
        assertEquals(expectedFileContent, actualFileContent);
    }
}
