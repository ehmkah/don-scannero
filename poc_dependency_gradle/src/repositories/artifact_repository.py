# connects to poc_dependencies server
from neo4j import GraphDatabase

from model import Artifact


class ArtifactRepository:

    def __init__(self, uri, user, password):
        self._driver = GraphDatabase.driver(uri, auth=(user, password), encrypted=False)

    def close(self):
        self._driver.close()

    def write(self, artifact: Artifact):
        with self._driver.session() as session:
            self.write_artifact(artifact, session)
            for dependency in artifact.depends_on:
                self.write_artifact(dependency, session)
                session.run("MATCH(a: Artifact), (b:Artifact)  "
                            "WHERE a.artifact_name = $source_artifact_name AND a.artifact_version= $source_artifact_version "
                            "AND b.artifact_name = $dependency_artifact_name AND b.artifact_version = $dependency_artifact_version"
                            " CREATE(a) - [r: DEPENDS_ON]->(b)",
                            source_artifact_name=artifact.artifact_name,
                            source_artifact_version=artifact.artifact_version,
                            dependency_artifact_name=dependency.artifact_name,
                            dependency_artifact_version=dependency.artifact_version)

    def write_artifact(self, artifact, session):
        session.run("CREATE (a:Artifact {artifact_name: $artifact_name, artifact_version: $artifact_version})",
                    artifact_name=artifact.artifact_name, artifact_version=artifact.artifact_version)
