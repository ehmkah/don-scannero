package de.ehmkah.products.poc_dependencies.model;

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
}
