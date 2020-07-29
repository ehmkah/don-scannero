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

    public void writeDependency(final Dependency message) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public void writeArtifact(String groupID, String artifactId, String version) {
        try (Session session = driver.session()) {
            session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Artifact) " +
                                    "SET a.artifact_name = $artifact_name, " +
                                    "a.version = $version " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("artifact_name", groupID + ":" + artifactId,
                                    "version", version));
                    return result.single().get(0).asString();
                }
            });
        }
    }
}