from model import Artifact
from src.repositories.artifact_repository import ArtifactRepository


def test_write():
    sut = ArtifactRepository("bolt://localhost:7687", "", "")
    artifact = Artifact("hallo", "123")
    sut.write(artifact=artifact)
    pass
