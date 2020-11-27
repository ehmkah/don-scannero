package com.github.foo;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lovett Li
 */
public class GradleDependencyUpdater {

    private List<ASTNode> nodes;
    private List<String> gradleFileContents;

    public GradleDependencyUpdater(List<String> gradleFileContents) throws MultipleCompilationErrorsException, IOException {
        this(gradleFileContents.stream().collect(Collectors.joining("\n")));
        this.gradleFileContents = gradleFileContents;
    }

    private GradleDependencyUpdater(String scriptContents) throws MultipleCompilationErrorsException {
        AstBuilder builder = new AstBuilder();
        nodes = builder.buildFromString(scriptContents);
    }

    public FindDependenciesVisitor insertDependency(String dependency) throws IOException {
        FindDependenciesVisitor visitor = new FindDependenciesVisitor();
        walkScript(visitor);

        if (visitor.getDependenceLineNum() == -1) {
            if (!dependency.startsWith("\t")) {
                dependency = "\t" + dependency;
                ;
            }

            gradleFileContents.add("");
            gradleFileContents.add("dependencies {");
            gradleFileContents.add(dependency);
            gradleFileContents.add("}");
        } else {
            gradleFileContents.add(visitor.getDependenceLineNum() - 1, dependency);
        }

        return visitor;
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