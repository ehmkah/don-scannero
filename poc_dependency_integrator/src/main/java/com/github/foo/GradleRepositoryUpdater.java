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

/**
 * @author Lovett Li
 */
public class GradleRepositoryUpdater {

    private List<ASTNode> nodes;
    private File file;
    private List<String> gradleFileContents;

    public GradleRepositoryUpdater(File inputfile) throws MultipleCompilationErrorsException, IOException {
        this(IOUtils.toString(new FileInputStream(inputfile), "UTF-8"));
        this.file = inputfile;
        gradleFileContents = Files.readAllLines(Paths.get(file.toURI()));
    }

    private GradleRepositoryUpdater(String scriptContents) throws MultipleCompilationErrorsException {
        AstBuilder builder = new AstBuilder();
        nodes = builder.buildFromString(scriptContents);
    }

    public void insertApply() throws IOException {
        FindRepositoryVisitor visitor = new FindRepositoryVisitor();
        walkScript(visitor);

        if (visitor.getColumnNum() != -1) {
            gradleFileContents = Files.readAllLines(Paths.get(file.toURI()));
            StringBuilder builder = new StringBuilder(gradleFileContents.get(visitor.getDependenceLineNum() - 1));
            //builder.insert(visitor.getColumnNum() - 2, "\n" + applyString + "\n");
            String dep = builder.toString();

            /**if( CoreUtil.isWindows() )
             {
             dep.replace( "\n", "\r\n" );
             }
             else if( CoreUtil.isMac() )
             {
             dep.replace( "\n", "\r" );
             }
             */

            gradleFileContents.remove(visitor.getDependenceLineNum() - 1);
            gradleFileContents.add(visitor.getDependenceLineNum() - 1, dep);
        } else {
            gradleFileContents.add( visitor.getDependenceLineNum() - 1, "        }" );
            gradleFileContents.add( visitor.getDependenceLineNum() - 1, "            }" );
            gradleFileContents.add( visitor.getDependenceLineNum() - 1, "                snapshotsOnly()" );
            gradleFileContents.add( visitor.getDependenceLineNum() - 1, "            mavenContent {" );
            gradleFileContents.add( visitor.getDependenceLineNum() - 1, "            url \"https://oss.sonatype.org/content/repositories/snapshots\"" );
            gradleFileContents.add( visitor.getDependenceLineNum() - 1, "        maven {" );
        }

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