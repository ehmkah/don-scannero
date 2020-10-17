package de.ehmkah.products.poc_dependencies.model;

import java.util.Objects;

public class Artifact {

    private String name;

    private String groupId;

    private String version;

    public Artifact(String name, String groupId, String version) {
        this.name = name;
        this.groupId = groupId;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getVersion() {
        return version;
    }

    public String getArtifactname() {
        return groupId + ":" + name;
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "name='" + name + '\'' +
                ", groupId='" + groupId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(name, artifact.name) &&
                Objects.equals(groupId, artifact.groupId) &&
                Objects.equals(version, artifact.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, groupId, version);
    }
}
