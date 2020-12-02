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
public class GradleDependencyUpdater {

    private final List<ASTNode> nodes;
    private List<String> gradleFileContents;

    public GradleDependencyUpdater(List<String> gradleFileContents) throws MultipleCompilationErrorsException, IOException {
        this(gradleFileContents.stream().collect(Collectors.joining("\n")));
        this.gradleFileContents = gradleFileContents;
    }

    private GradleDependencyUpdater(String scriptContents) throws MultipleCompilationErrorsException {
        AstBuilder builder = new AstBuilder();
        nodes = builder.buildFromString(scriptContents);
    }

    public FindDependenciesVisitor insertDependency() throws IOException {
        FindDependenciesVisitor visitor = new FindDependenciesVisitor();
        walkScript(visitor);

        if (visitor.getDependenceLineNum() == -1) {

            gradleFileContents.add("");
            gradleFileContents.add("dependencies {");
            gradleFileContents.add("        classpath \"com.github.ehmkah:don-scannero:0.0.7-SNAPSHOT\"");
            gradleFileContents.add("}");
        } else {
            gradleFileContents.add(visitor.getDependenceLineNum() - 1, "        classpath \"com.github.ehmkah:don-scannero:0.0.7-SNAPSHOT\"");
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