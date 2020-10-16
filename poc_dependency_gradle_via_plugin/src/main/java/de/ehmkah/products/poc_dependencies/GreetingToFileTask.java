package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import de.ehmkah.products.poc_dependencies.writer.ScanneroWriter;
import de.ehmkah.products.poc_dependencies.writer.SystemOutWriter;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.diagnostics.AbstractReportTask;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class GreetingToFileTask extends DefaultTask {

    private ScanneroWriter scanneroWriter;

    private Scanner scanner;
    private File outputDir = new File(getProject().getBuildDir().getAbsolutePath() + "/scannero/");

    @Inject
    public GreetingToFileTask() {
        scanneroWriter = new SystemOutWriter();
        //neo4Repository = new Neo4Repository("bolt://localhost:7687", "neo4j", "password");
        scanner = new Scanner();
    }

    @Input
    String groupID = "dummyGroupId";

    @Input
    String artifactId = "dummyArtifactId";

    @Input
    String version = "dummyVersion";

    @OutputDirectory
    public File getOutputDir() {
        return outputDir;
    }


    @TaskAction
    public void scan() throws IOException {
        System.out.println("groupID:" + groupID);
        System.out.println("artifactId:" + artifactId);
        System.out.println("version:" + version);
        Project project = getProject();
        Artifact basis = new Artifact(artifactId, groupID, version);

        Map<Project, Set<Task>> allTasks = getProject().getAllTasks(false);
        for (Set<Task> tasks : allTasks.values()) {
            for (Task task : tasks) {
                if (task.getName().startsWith("dependencies")) {
                    getProject().getBuildDir().createNewFile();
                    AbstractReportTask generateDependencies = (AbstractReportTask) task;
                    generateDependencies.setOutputFile(new File(outputDir.getCanonicalPath() + "/parsedDependencies"));
                    generateDependencies.generate();
                }
            }
        }
        scanneroWriter.writeArtifact(basis);
        List<Artifact> artifacts = scanner.extractDependencies(project);
        scanneroWriter.writeArtifacts(basis, artifacts);
    }
}