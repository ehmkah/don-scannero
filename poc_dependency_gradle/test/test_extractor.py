import pytest

from extractor import parseDependency, parse

example_input = "Configure project :\\n[gradle-intellij-plugin :example1] Cannot read builtin registry cache. Run with --debug option to get more log output.\\n\\n> Task :dependencies\\n\\n------------------------------------------------------------\\nRoot project\\n------------------------------------------------------------\\n\\n-api (n)\\nNo dependencies\\n\\n-runtime (n)\\nNo dependencies\\n\\nannotationProcessor - Annotation processors and their dependencies for source set 'main'.\\nNo dependencies\\n\\napi - API dependencies for compilation 'main' (target  (jvm)). (n)\\nNo dependencies\\n\\napiDependenciesMetadata\\nNo dependencies\\n\\napiElements - API elements for main. (n)\\nNo dependencies\\n\\narchives - Configuration for archive artifacts. (n)\\nNo dependencies\\n\\ncompileClasspath - Compile classpath for compilation 'main' (target  (jvm)).\\n\\\\--- com.jetbrains:ideaIC:2020.1\\n\\ncompileOnly - Compile only dependencies for compilation 'main' (target  (jvm)). (n)\\nNo dependencies\\n\\ncompileOnlyDependenciesMetadata\\n\\\\--- com.jetbrains:ideaIC:2020.1\\n\\ndefault - Configuration for default artifacts. (n)\\nNo dependencies\\n\\nidea\\n\\\\--- com.jetbrains:ideaIC:2020.1\\n\\nideaPlugins\\nNo dependencies\\n\\nimplementation - Implementation only dependencies for compilation 'main' (target  (jvm)). (n)\\nNo dependencies\\n\\nimplementationDependenciesMetadata\\nNo dependencies\\n\\nkotlinCompilerClasspath\\n\\\\--- org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.72\\n     +--- org.jetbrains.kotlin:kotlin-stdlib:1.3.72\\n     |    +--- org.jetbrains.kotlin:kotlin-stdlib-common:1.3.72\\n     |    \\\\--- org.jetbrains:annotations:13.0\\n     +--- org.jetbrains.kotlin:kotlin-script-runtime:1.3.72\\n     +--- org.jetbrains.kotlin:kotlin-reflect:1.3.72\\n     |    \\\\--- org.jetbrains.kotlin:kotlin-stdlib:1.3.72 (*)\\n     +--- org.jetbrains.kotlin:kotlin-daemon-embeddable:1.3.72\\n     \\\\--- org.jetbrains.intellij.deps:trove4j:1.0.20181211\\n\\nkotlinCompilerPluginClasspath\\n\\\\--- org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.3.72\\n     +--- org.jetbrains.kotlin:kotlin-scripting-compiler-impl-embeddable:1.3.72\\n     |    +--- org.jetbrains.kotlin:kotlin-scripting-common:1.3.72\\n     |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.3.72\\n     |    |    |    +--- org.jetbrains.kotlin:kotlin-stdlib-common:1.3.72\\n     |    |    |    \\\\--- org.jetbrains:annotations:13.0\\n     |    |    \\\\--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1\\n     |    +--- org.jetbrains.kotlin:kotlin-scripting-jvm:1.3.72\\n     |    |    +--- org.jetbrains.kotlin:kotlin-script-runtime:1.3.72\\n     |    |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.3.72 (*)\\n     |    |    \\\\--- org.jetbrains.kotlin:kotlin-scripting-common:1.3.72 (*)\\n     |    +--- org.jetbrains.kotlin:kotlin-stdlib:1.3.72 (*)\\n     |    \\\\--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1\\n     \\\\--- org.jetbrains.kotlin:kotlin-stdlib:1.3.72 (*)\\n\\nkotlinNativeCompilerPluginClasspath\\nNo dependencies\\n\\nkotlinScriptDef - Script filename extensions discovery classpath configuration\\nNo dependencies\\n\\nkotlinScriptDefExtensions\\nNo dependencies\\n\\nruntimeClasspath - Runtime classpath of compilation 'main' (target  (jvm)).\\nNo dependencies\\n\\nruntimeElements - Elements of runtime for main. (n)\\nNo dependencies\\n\\nruntimeOnly - Runtime only dependencies for compilation 'main' (target  (jvm)). (n)\\nNo dependencies\\n\\nruntimeOnlyDependenciesMetadata\\nNo dependencies\\n\\nsourceArtifacts (n)\\nNo dependencies\\n\\ntestAnnotationProcessor - Annotation processors and their dependencies for source set 'test'.\\nNo dependencies\\n\\ntestApi - API dependencies for compilation 'test' (target  (jvm)). (n)\\nNo dependencies\\n\\ntestApiDependenciesMetadata\\nNo dependencies\\n\\ntestCompileClasspath - Compile classpath for compilation 'test' (target  (jvm)).\\n+--- com.jetbrains:ideaIC:2020.1\\n+--- org.junit.jupiter:junit-jupiter:5.6.2\\n|    +--- org.junit:junit-bom:5.6.2\\n|    |    +--- org.junit.jupiter:junit-jupiter:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-api:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-params:5.6.2 (c)\\n|    |    \\\\--- org.junit.platform:junit-platform-commons:1.6.2 (c)\\n|    +--- org.junit.jupiter:junit-jupiter-api:5.6.2\\n|    |    +--- org.junit:junit-bom:5.6.2 (*)\\n|    |    +--- org.apiguardian:apiguardian-api:1.1.0\\n|    |    +--- org.opentest4j:opentest4j:1.2.0\\n|    |    \\\\--- org.junit.platform:junit-platform-commons:1.6.2\\n|    |         +--- org.junit:junit-bom:5.6.2 (*)\\n|    |         \\\\--- org.apiguardian:apiguardian-api:1.1.0\\n|    \\\\--- org.junit.jupiter:junit-jupiter-params:5.6.2\\n|         +--- org.junit:junit-bom:5.6.2 (*)\\n|         +--- org.apiguardian:apiguardian-api:1.1.0\\n|         \\\\--- org.junit.jupiter:junit-jupiter-api:5.6.2 (*)\\n\\\\--- com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0\\n     \\\\--- org.mockito:mockito-core:2.23.0\\n          +--- net.bytebuddy:byte-buddy:1.9.0\\n          +--- net.bytebuddy:byte-buddy-agent:1.9.0\\n          \\\\--- org.objenesis:objenesis:2.6\\n\\ntestCompileOnly - Compile only dependencies for compilation 'test' (target  (jvm)). (n)\\nNo dependencies\\n\\ntestCompileOnlyDependenciesMetadata\\nNo dependencies\\n\\ntestImplementation - Implementation only dependencies for compilation 'test' (target  (jvm)). (n)\\n+--- org.junit.jupiter:junit-jupiter:5.6.2 (n)\\n\\\\--- com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0 (n)\\n\\ntestImplementationDependenciesMetadata\\n+--- com.jetbrains:ideaIC:2020.1\\n+--- org.junit.jupiter:junit-jupiter:5.6.2\\n|    +--- org.junit:junit-bom:5.6.2\\n|    |    +--- org.junit.jupiter:junit-jupiter:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-api:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-params:5.6.2 (c)\\n|    |    \\\\--- org.junit.platform:junit-platform-commons:1.6.2 (c)\\n|    +--- org.junit.jupiter:junit-jupiter-api:5.6.2\\n|    |    +--- org.junit:junit-bom:5.6.2 (*)\\n|    |    +--- org.apiguardian:apiguardian-api:1.1.0\\n|    |    +--- org.opentest4j:opentest4j:1.2.0\\n|    |    \\\\--- org.junit.platform:junit-platform-commons:1.6.2\\n|    |         +--- org.junit:junit-bom:5.6.2 (*)\\n|    |         \\\\--- org.apiguardian:apiguardian-api:1.1.0\\n|    \\\\--- org.junit.jupiter:junit-jupiter-params:5.6.2\\n|         +--- org.junit:junit-bom:5.6.2 (*)\\n|         +--- org.apiguardian:apiguardian-api:1.1.0\\n|         \\\\--- org.junit.jupiter:junit-jupiter-api:5.6.2 (*)\\n\\\\--- com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0\\n     \\\\--- org.mockito:mockito-core:2.23.0\\n          +--- net.bytebuddy:byte-buddy:1.9.0\\n          +--- net.bytebuddy:byte-buddy-agent:1.9.0\\n          \\\\--- org.objenesis:objenesis:2.6\\n\\ntestKotlinScriptDef - Script filename extensions discovery classpath configuration\\nNo dependencies\\n\\ntestKotlinScriptDefExtensions\\nNo dependencies\\n\\ntestRuntimeClasspath - Runtime classpath of compilation 'test' (target  (jvm)).\\n+--- com.jetbrains:ideaIC:2020.1\\n+--- org.junit.jupiter:junit-jupiter:5.6.2\\n|    +--- org.junit:junit-bom:5.6.2\\n|    |    +--- org.junit.jupiter:junit-jupiter:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-api:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-engine:5.6.2 (c)\\n|    |    +--- org.junit.jupiter:junit-jupiter-params:5.6.2 (c)\\n|    |    +--- org.junit.platform:junit-platform-commons:1.6.2 (c)\\n|    |    \\\\--- org.junit.platform:junit-platform-engine:1.6.2 (c)\\n|    +--- org.junit.jupiter:junit-jupiter-api:5.6.2\\n|    |    +--- org.junit:junit-bom:5.6.2 (*)\\n|    |    +--- org.apiguardian:apiguardian-api:1.1.0\\n|    |    +--- org.opentest4j:opentest4j:1.2.0\\n|    |    \\\\--- org.junit.platform:junit-platform-commons:1.6.2\\n|    |         +--- org.junit:junit-bom:5.6.2 (*)\\n|    |         \\\\--- org.apiguardian:apiguardian-api:1.1.0\\n|    +--- org.junit.jupiter:junit-jupiter-params:5.6.2\\n|    |    +--- org.junit:junit-bom:5.6.2 (*)\\n|    |    +--- org.apiguardian:apiguardian-api:1.1.0\\n|    |    \\\\--- org.junit.jupiter:junit-jupiter-api:5.6.2 (*)\\n|    \\\\--- org.junit.jupiter:junit-jupiter-engine:5.6.2\\n|         +--- org.junit:junit-bom:5.6.2 (*)\\n|         +--- org.apiguardian:apiguardian-api:1.1.0\\n|         +--- org.junit.platform:junit-platform-engine:1.6.2\\n|         |    +--- org.junit:junit-bom:5.6.2 (*)\\n|         |    +--- org.apiguardian:apiguardian-api:1.1.0\\n|         |    +--- org.opentest4j:opentest4j:1.2.0\\n|         |    \\\\--- org.junit.platform:junit-platform-commons:1.6.2 (*)\\n|         \\\\--- org.junit.jupiter:junit-jupiter-api:5.6.2 (*)\\n\\\\--- com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0\\n     \\\\--- org.mockito:mockito-core:2.23.0\\n          +--- net.bytebuddy:byte-buddy:1.9.0\\n          +--- net.bytebuddy:byte-buddy-agent:1.9.0\\n          \\\\--- org.objenesis:objenesis:2.6\\n\\ntestRuntimeOnly - Runtime only dependencies for compilation 'test' (target  (jvm)). (n)\\nNo dependencies\\n\\ntestRuntimeOnlyDependenciesMetadata\\nNo dependencies\\n\\n(c) - dependency constraint\\n(*) - dependencies omitted (listed previously)\\n\\n(n) - Not resolved (configuration is not meant to be resolved)\\n\\nA web-based, searchable dependency report is available by adding the --scan option.\\n\\nBUILD SUCCESSFUL in 1s\\n1 actionable task: 1 executed\\n"


def test_foo():
    actual = parseDependency(example_input)
    assert len(actual.depends_on) == 5
    assert actual.depends_on[0].artifact_name == "com.jetbrains:ideaIC"
    assert actual.depends_on[0].artifact_version == "2020.1"
    assert actual.depends_on[1].artifact_name == "org.jetbrains.kotlin:kotlin-compiler-embeddable"
    assert actual.depends_on[1].artifact_version == "1.3.72"
    assert actual.depends_on[2].artifact_name == "org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable"
    assert actual.depends_on[2].artifact_version == "1.3.72"
    assert actual.depends_on[3].artifact_name == "org.junit.jupiter:junit-jupiter"
    assert actual.depends_on[3].artifact_version == "5.6.2"
    assert actual.depends_on[4].artifact_name == "com.nhaarman.mockitokotlin2:mockito-kotlin"
    assert actual.depends_on[4].artifact_version == "2.2.0"

def test_parse():
    actual = parse("\\\\--- com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    assert actual.artifact_name == "com.nhaarman.mockitokotlin2:mockito-kotlin"
    assert actual.artifact_version == "2.2.0"
