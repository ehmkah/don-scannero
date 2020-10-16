package de.ehmkah.products.poc_dependencies.writer;

import de.ehmkah.products.poc_dependencies.model.Artifact;

import java.util.List;

public class SystemOutWriter implements ScanneroWriter {

    @Override
    public void writeArtifact(Artifact artifact) {
        System.out.println(artifact);
    }

    @Override
    public void writeArtifacts(Artifact basis, List<Artifact> artifacts) {
        artifacts.forEach(System.out::println);
    }
}
