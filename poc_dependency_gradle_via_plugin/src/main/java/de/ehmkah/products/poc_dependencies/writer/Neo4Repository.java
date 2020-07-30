package de.ehmkah.products.poc_dependencies.writer;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import org.neo4j.driver.*;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class Neo4Repository implements AutoCloseable {
    private final Driver driver;

    public Neo4Repository(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    public void writeDependency(Artifact basis, Artifact dependency) {
        writeArtifact(dependency);
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
                            parameters("source_artifact_name", basis.getArtifactname(),
                                    "source_artifact_version", basis.getVersion(),
                                    "dependency_artifact_name", dependency.getArtifactname(),
                                    "dependency_artifact_version", dependency.getVersion()));
                    return result.single().get(0).asString();
                }
            });
        }
    }


    public void writeArtifact(Artifact artifact) {
        try (Session session = driver.session()) {
            session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Artifact) " +
                                    "SET a.artifact_name = $artifact_name, " +
                                    "a.artifact_version = $version " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("artifact_name", artifact.getArtifactname(),
                                    "version", artifact.getVersion()));
                    return result.single().get(0).asString();
                }
            });
        }
    }

    public void writeArtifacts(Artifact basis, List<Artifact> artifacts) {
        for (Artifact artifact : artifacts) {
            writeDependency(basis, artifact);
        }
    }
}