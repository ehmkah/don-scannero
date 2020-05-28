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
            session.run("CREATE (a:Artifact {artifact_name: $artifact_name})", artifact_name=artifact.artifact_name)
