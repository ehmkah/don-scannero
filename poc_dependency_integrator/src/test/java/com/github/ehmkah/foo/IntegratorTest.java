package com.github.ehmkah.foo;

import com.github.foo.integrator.Integrator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegratorTest {

    private Integrator sut = new Integrator();

    @Test
    public void testExampl1() throws IOException {
        // GIVEN
        final File inputFile = new File("src/test/resources/given.example1.gradle");
        final File expectedFile = new File("src/test/resources/expected.example1.gradle");
        List<String> expectedFileContent = Files.readAllLines(Paths.get(expectedFile.toURI()));

        // WHEN
        List<String> actualFileContent = sut.integrate(inputFile);

        // THEN
        assertEquals(expectedFileContent, actualFileContent);
    }
}
