package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

public class ParserTest {

    private final Parser sut = new Parser();

    @Test
    public void testParse() {
// GIVEN
        InputStream inputStream = ParserTest.class.getResourceAsStream("/exampleDependencies.txt");
// WHEN
        List<Artifact> actual = sut.parse(inputStream);
// THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(new Artifact("guava", "com.google.guava", "29.0-jre"), actual.get(0));
    }


}
