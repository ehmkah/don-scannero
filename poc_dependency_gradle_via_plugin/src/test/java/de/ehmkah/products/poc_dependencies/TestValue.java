package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;

import java.util.Optional;

public class TestValue {

    String given;
    Optional<Artifact> expected;

    public TestValue(String given, Artifact expected) {
        this.given = given;
        this.expected = Optional.of(expected);
    }


    public TestValue(String given) {
        this.given = given;
        this.expected = Optional.empty();
    }


    public String getGiven() {
        return given;
    }

    public Optional<Artifact> getExpected() {
        return expected;
    }
}
