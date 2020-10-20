package de.ehmkah.products.poc_dependencies.writer;

import de.ehmkah.products.poc_dependencies.model.Artifact;

import java.util.List;

public interface ScanneroWriter {

    void writeArtifact(Artifact artifact);

    void writeArtifacts(Artifact basis, List<Artifact> artifacts);
}
