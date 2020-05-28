import subprocess

from extractor import parseDependency
from src.repositories.artifact_repository import ArtifactRepository

result = subprocess.run(["./gradlew", "dependencies"], capture_output=True)
dependencies = parseDependency(str(result.stdout))


def run():
    artifactory_repository = ArtifactRepository("bolt://localhost:7687", "", "")
    for dependency in dependencies.depends_on:
        artifactory_repository.write(dependency)


run()
