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
public class GradleApplyUpdater {

    private final List<ASTNode> nodes;
    private List<String> gradleFileContents;

    public GradleApplyUpdater(List<String> gradleFileContents) throws MultipleCompilationErrorsException, IOException {
        this(gradleFileContents.stream().collect(Collectors.joining("\n")));
        this.gradleFileContents = gradleFileContents;
    }

    private GradleApplyUpdater(String scriptContents) throws MultipleCompilationErrorsException {
        AstBuilder builder = new AstBuilder();
        nodes = builder.buildFromString(scriptContents);
    }

    public FindApplyVisitor insertApply() throws IOException {
        FindApplyVisitor visitor = new FindApplyVisitor();
        walkScript(visitor);

        gradleFileContents.add(visitor.getDependenceLineNum() - 1, "apply plugin: \"com.github.ehmkah.don-scannero\"");

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