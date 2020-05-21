import subprocess

from extractor import parseDependency

result = subprocess.run(["./gradlew", "dependencies"], capture_output=True)
dependencies = parseDependency(str(result.stdout))

for dependency in dependencies.depends_on:
    print(dependency.artifact_name+":"+dependency.artifact_version)
