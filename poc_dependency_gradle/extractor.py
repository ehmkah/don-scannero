# extracts dependencies from gradle projects
import subprocess
subprocess.call(["./gradlew", "dependencies"])
# parsing goes here
