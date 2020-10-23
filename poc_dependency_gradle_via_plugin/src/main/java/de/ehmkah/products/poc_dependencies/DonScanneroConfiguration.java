package de.ehmkah.products.poc_dependencies;

/**
 * Holds configuration for task.
 */
public class DonScanneroConfiguration {

    private String version;
    private String group;

    public DonScanneroConfiguration(String version, String group) {
        this.version = version;
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public String getGroup() {
        return group;
    }
}
