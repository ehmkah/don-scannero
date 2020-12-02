package com.github.foo.integrator;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lovett Li
 */
public class GradleRepositoryUpdater {

    private List<ASTNode> nodes;
    private List<String> gradleFileContents;

    public GradleRepositoryUpdater(List<String> gradleFileContents) throws MultipleCompilationErrorsException, IOException {
        this(gradleFileContents.stream().collect(Collectors.joining("\n")));
        this.gradleFileContents = gradleFileContents;
    }

    private GradleRepositoryUpdater(String scriptContents) throws MultipleCompilationErrorsException {
        AstBuilder builder = new AstBuilder();
        nodes = builder.buildFromString(scriptContents);
    }

    public void insertApply() throws IOException {
        FindRepositoryVisitor visitor = new FindRepositoryVisitor();
        walkScript(visitor);

        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "        }");
        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "            }");
        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "                snapshotsOnly()");
        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "            mavenContent {");
        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "            url \"https://oss.sonatype.org/content/repositories/snapshots\"");
        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "        maven {");

    }

    public void walkScript(GroovyCodeVisitor visitor) {
        for (ASTNode node : nodes) {
            node.visit(visitor);
        }
    }

    public List<String> getGradleFileContents() {
        return gradleFileContents;
    }

}