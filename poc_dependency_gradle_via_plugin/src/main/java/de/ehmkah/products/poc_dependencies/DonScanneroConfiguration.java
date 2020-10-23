package de.ehmkah.products.poc_dependencies;

/**
 * Holds configuration for task.
 */
public class DonScanneroConfiguration {

    private String version;
    private String group;
    private String name;

    public DonScanneroConfiguration(String name, String group, String version) {
        this.version = version;
        this.group = group;
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return this.name;
    }
}
