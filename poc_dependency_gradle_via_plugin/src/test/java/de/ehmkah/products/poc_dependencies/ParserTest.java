package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ParserTest {

    private final Parser sut = new Parser();

    static Stream<TestValue> valueProvider() {
        List<TestValue> values = new LinkedList<>();
        values.add(new TestValue("+--- com.google.guava:guava:29.0-jre", new Artifact("guava", "com.google.guava", "29.0-jre")));
        values.add(new TestValue("\\--- org.springframework.boot:spring-boot-starter-web -> 2.3.3.RELEASE", new Artifact("org.springframework.boot", "spring-boot-starter-web", "2.3.3.RELEASE")));

        // currently not parsed
        values.add(new TestValue("|    +--- com.google.guava:failureaccess:1.0.1"));

        // intented not to be parse
        values.add(new TestValue("+--- com.google.guava:guava:29.0-jre (n)"));


        Stream<TestValue> result = values.stream();
        return result;
    }

    @Test
    public void testParse() {
// GIVEN
        InputStream inputStream = ParserTest.class.getResourceAsStream("/exampleDependencies.txt");
// WHEN
        List<Artifact> actual = sut.parse(inputStream);
// THEN
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(4, actual.size());
        Assertions.assertEquals(new Artifact("guava", "com.google.guava", "29.0-jre"), actual.get(0));
        Assertions.assertEquals(new Artifact("spring-boot-starter-web", "org.springframework.boot", "2.3.3.RELEASE"), actual.get(1));
        Assertions.assertEquals(new Artifact("junit-jupiter-api", "org.junit.jupiter", "5.6.2"), actual.get(2));
        Assertions.assertEquals(new Artifact("junit-jupiter-engine", "org.junit.jupiter", "5.6.2"), actual.get(3));
    }

    @ParameterizedTest
    @MethodSource("valueProvider")
    void testParseLine(TestValue value) {
        // GIVEN
        // WHEN
        Optional<Artifact> actual = sut.extractArtifact(value.getGiven());

        // THEN
        Assertions.assertEquals(value.getExpected(), actual);
    }


}
