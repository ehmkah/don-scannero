package de.ehmkah.products.poc_dependencies;

import org.gradle.api.tasks.Input;

public class DonScanneroPluginConfiguration {
    /** Default values if you run ne04j locally.**/
    private String neo4jUser = "neo4j";
    private String neo4jPassword = "password";
    private String neo4jUri = "bolt://localhost:7687";

    public String getNeo4jUser() {
        return neo4jUser;
    }

    public void setNeo4jUser(String neo4jUser) {
        this.neo4jUser = neo4jUser;
    }

    public String getNeo4jPassword() {
        return neo4jPassword;
    }

    public void setNeo4jPassword(String neo4jPassword) {
        this.neo4jPassword = neo4jPassword;
    }

    public String getNeo4jUri() {
        return neo4jUri;
    }

    public void setNeo4jUri(String neo4jUri) {
        this.neo4jUri = neo4jUri;
    }
}
