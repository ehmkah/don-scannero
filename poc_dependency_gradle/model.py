class Artifact:
    artifact_name = ""
    artifact_version = ""
    depends_on = []

    def __init__(self, artifact_name, artifact_version):
        self.artifact_name = artifact_name
        self.artifact_version = artifact_version
