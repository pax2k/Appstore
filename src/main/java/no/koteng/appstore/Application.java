package no.koteng.appstore;

import java.io.Serializable;

public class Application implements Serializable {
    private final String applicationName;
    private final String description;
    private final String imageUrl;
    private final Artifact artifact;

    public Application(String applicationName, String description, String imageUrl, Artifact artifact) {
        this.applicationName = applicationName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.artifact = artifact;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
