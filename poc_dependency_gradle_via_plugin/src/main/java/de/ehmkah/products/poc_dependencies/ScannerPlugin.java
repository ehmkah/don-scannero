package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class ScannerPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("scanner", ScannerPlugin.class);
        Action<? super Task> action = new Action<Task>() {
            @Override
            public void execute(Task task) {
                System.out.println("hu");
            }
        };
        project.task("scanner", action);
    }
}
/**
 *
 project.task("hello1", GreetingsAction())

 .task('hello1') {
 * doLast {
 * println "group:" + extension.groupId
 * println "artifact" + project.name
 * print "version" + extension.version
 * print group
 */

/**
 * class GreetingPlugin implements Plugin<Project> {
 * void apply(Project project) {
 * // Add the 'greeting' extension object
 * def extension = project.extensions.create('greeting', GreetingPluginExtension)
 * // Add a task that uses configuration from the extension object
 * project.task('hello1') {
 * doLast {
 * println "group:" + extension.groupId
 * println "artifact" + project.name
 * print "version" + extension.version
 * print group
 * }
 * }
 * }
 * <p>
 * class GreetingPluginExtension {
 * String message = 'Hello from GreetingPlugin'
 * String groupId
 * String version
 * }
 **/