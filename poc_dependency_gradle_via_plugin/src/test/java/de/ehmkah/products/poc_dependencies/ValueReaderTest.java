package de.ehmkah.products.poc_dependencies;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValueReaderTest {

    private ValueReader sut = new ValueReader();

    @Test()
    public void testReadValuesVersionNotSet() {
        // GIVEN
        Project project = ProjectBuilder.builder().build();

        // WHEN THEN
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                sut.readValues(project));
    }

    @Test()
    public void testReadValuesAllValuesSet() {
        // GIVEN
        Project project = ProjectBuilder.builder().build();
        project.setVersion("1.2.3");
        project.setGroup("com.github.ehmkah");

        // WHEN
        DonScanneroConfiguration actual = sut.readValues(project);

        // THEN
        Assertions.assertEquals("1.2.3", actual.getVersion());
        Assertions.assertEquals("com.github.ehmkah", actual.getGroup());
        Assertions.assertEquals("test", actual.getName());
    }

    @Test
    public void testReadValuesGroupNotSet() {
        // GIVEN
        Project project = ProjectBuilder.builder().build();
        project.setVersion("1.2.3");

        // WHEN
        // THEN
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                sut.readValues(project));
    }

}