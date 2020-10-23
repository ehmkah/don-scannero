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

    public static final String PREFIX_1 = "+--- ";
    private static final String PREFIX_2 = "\\--- ";

    public List<Artifact> parse(InputStream inputStream) {
        Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        List<Artifact> result =
                lines.map(line -> extractArtifact(line))
                        .filter(element -> element.isPresent())
                        .map(element -> element.get())
                        .distinct()
                        .collect(Collectors.toList());

        return result;
    }

    Optional<Artifact> extractArtifact(String line) {
        if (line.startsWith(PREFIX_1) || line.startsWith(PREFIX_2)) {
            line = removeTrailingGarbage(line);
            try {
                String[] artifactParts = line.split(":");
                String version = artifactParts[2];
                if (!version.contains(" ")) {
                    return Optional.of(new Artifact(artifactParts[0], artifactParts[1], version));
                } else {
                    return Optional.empty();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                try {
                    String[] artifactParts = line.split(":");
                    String[] artifactAndVersion = artifactParts[1].split("->");
                    String version = artifactAndVersion[1].trim();
                    if (!version.contains(" ")) {
                        String groupId = artifactParts[0];
                        return Optional.of(new Artifact(groupId, artifactAndVersion[0].trim(), version));
                    } else {
                        return Optional.empty();
                    }
                } catch (Exception e2) {
                    System.out.println("Oops - parsing dependency " + line + " not supported. Please open an issue.");
                }
            }
        }

        return Optional.empty();
    }

    private String removeTrailingGarbage(String artifactPart) {
        return artifactPart.replace(PREFIX_1, "").replace(PREFIX_2, "");
    }
}
