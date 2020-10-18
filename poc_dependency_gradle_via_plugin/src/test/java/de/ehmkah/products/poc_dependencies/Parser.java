package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    public static final String PREFIX = "+--- ";

    public List<Artifact> parse(InputStream inputStream) {
        Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        List<Artifact> result =
                lines.map(line -> extractArtifact(line))
                        .filter(element -> element.isPresent())
                        .map(element -> element.get())
                        .collect(Collectors.toList());

        return result;
    }

    Optional<Artifact> extractArtifact(String line) {
        if (line.startsWith(PREFIX)) {
            try {
                String[] artifactParts = line.split(":");
                return Optional.of(new Artifact(artifactParts[1], artifactParts[0].replace(PREFIX, ""), artifactParts[2]));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Oops - parsing dependency " + line + " not supported. Please open an issue.");
            }
        }

        return Optional.empty();
    }
}
