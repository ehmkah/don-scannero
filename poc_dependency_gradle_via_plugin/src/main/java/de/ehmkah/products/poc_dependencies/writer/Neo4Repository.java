package de.ehmkah.products.poc_dependencies.writer;

import org.gradle.api.artifacts.Dependency;
import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class Neo4Repository implements AutoCloseable {
    private final Driver driver;

    public Neo4Repository(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void writeDependency(String groupID, String artifactId, String version, final Dependency dependency) {
        writeArtifact(dependency.getGroup(), dependency.getName(), dependency.getVersion());
        try (Session session = driver.session()) {
            session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    String query = "MATCH(a: Artifact), (b:Artifact)  " +
                            "WHERE " +
                            "a.artifact_name = $source_artifact_name AND " +
                            "a.artifact_version= $source_artifact_version " +
                            "AND b.artifact_name = $dependency_artifact_name " +
                            "AND b.artifact_version = $dependency_artifact_version" +
                            " CREATE(a) - [r: DEPENDS_ON]->(b) " +
                            "RETURN a.version + ', from node ' + id(a)";
                    Result result = tx.run(query + " ",
                            parameters("source_artifact_name", getArtifactname(groupID, artifactId),
                                    "source_artifact_version", version,
                                    "dependency_artifact_name", getArtifactname(dependency.getGroup(), dependency.getName()),
                                    "dependency_artifact_version", dependency.getVersion()));
                    return result.single().get(0).asString();
                }
            });
        }
    }

    private String getArtifactname(String groupID, String artifactId) {
        return groupID + ":" + artifactId;
    }

    public void writeArtifact(String groupID, String artifactId, String version) {
        try (Session session = driver.session()) {
            session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Artifact) " +
                                    "SET a.artifact_name = $artifact_name, " +
                                    "a.artifact_version = $version " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("artifact_name", getArtifactname(groupID, artifactId),
                                    "version", version));
                    return result.single().get(0).asString();
                }
            });
        }
    }
}