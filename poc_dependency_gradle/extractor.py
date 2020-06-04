# extracts dependencies from gradle projects
from model import Artifact

direct_dependency_identifiers = ["\\\\--- ", "+--- "]

def parse(line):
    replaced = line
    for identifier in direct_dependency_identifiers:
        replaced = replaced.replace(identifier, "")
    replaced = replaced.replace(" -> ", ":")
    splitted = replaced.split(":")
    return Artifact(splitted[0] + ":" + splitted[1], splitted[2])


def parseDependency(parse_input: str) -> Artifact:
    result = Artifact("unknown", "unknown")
    for line in parse_input.split("\\n"):
        for identifier in direct_dependency_identifiers:
            if line.startswith(identifier):
                artifact = parse(line)
                artifact_already_added = False
                for existing_dependency in result.depends_on:
                    artifact_already_added = artifact_already_added or existing_dependency.artifact_name == artifact.artifact_name
                if artifact_already_added == False:
                    result.depends_on.append(artifact)
        pass
    return result
