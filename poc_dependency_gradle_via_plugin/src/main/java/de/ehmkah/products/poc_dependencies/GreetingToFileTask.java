package de.ehmkah.products.poc_dependencies;

import de.ehmkah.products.poc_dependencies.model.Artifact;
import de.ehmkah.products.poc_dependencies.writer.Neo4Repository;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class GreetingToFileTask extends DefaultTask {

    @Input
    String groupID = "dummyGroupId";
    @Input
    String artifactId = "dummyArtifactId";
    @Input
    String version = "dummyVersion";
    private ScanneroWriter scanneroWriter;
    private Scanner scanner;
    private Parser parser;
    private File outputDir = new File(getProject().getBuildDir().getAbsolutePath() + "/scannero/");

    @Inject
    public GreetingToFileTask() {
        //scanneroWriter = new SystemOutWriter();
        scanneroWriter = new Neo4Repository("bolt://localhost:7687", "neo4j", "password");
        scanner = new Scanner();
        parser = new Parser();
    }

    @OutputDirectory
    public File getOutputDir() {
        return outputDir;
    }


    @TaskAction
    public void scan() throws IOException {
        System.out.println("groupID:" + groupID);
        System.out.println("artifactId:" + artifactId);
        System.out.println("version:" + version);
        Artifact basis = new Artifact(artifactId, groupID, version);

        Map<Project, Set<Task>> allTasks = getProject().getAllTasks(false);
        String fileName = outputDir.getCanonicalPath() + "/parsedDependencies";
        for (Set<Task> tasks : allTasks.values()) {
            for (Task task : tasks) {
                if (task.getName().startsWith("dependencies")) {
                    getProject().getBuildDir().createNewFile();
                    AbstractReportTask generateDependencies = (AbstractReportTask) task;

                    generateDependencies.setOutputFile(new File(fileName));
                    generateDependencies.generate();
                }
            }
        }
        FileInputStream fileInputStream = new FileInputStream(fileName);
        List<Artifact> artifacts = parser.parse(fileInputStream);
        scanneroWriter.writeArtifact(basis);
        scanneroWriter.writeArtifacts(basis, artifacts);
    }
}