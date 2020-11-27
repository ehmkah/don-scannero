package com.github.ehmkah.foo;

import com.github.foo.FindDependenciesVisitor;
import com.github.foo.GradleDependencyUpdater;
import com.github.foo.Integrator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegratorTest {

    private Integrator sut = new Integrator();

    @Test
    public void testExampl1() throws IOException {
        final File inputFile = new File( "given.example1.gradle" );

        GradleDependencyUpdater updater = new GradleDependencyUpdater( inputFile );

        FindDependenciesVisitor visitor = updater.insertDependency(
                "\tcompile group: \"com.liferay\", name:\"com.liferay.bookmarks.api\", version:\"1.0.0\"" );

        int dependenceLineNum = visitor.getDependenceLineNum();

        assertEquals( -1, dependenceLineNum );

        //Files.write( outputfile.toPath(), updater.getGradleFileContents(), StandardCharsets.UTF_8 );

        final File expectedOutputFile = new File( "expected.example1.gradle" );

        //assertEquals(
        //        CoreUtil.readStreamToString( new FileInputStream( expectedOutputFile ) ),
        //        CoreUtil.readStreamToString( new FileInputStream( outputfile ) ) );
    }
}
