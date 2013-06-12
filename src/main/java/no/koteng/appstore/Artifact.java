package no.koteng.appstore;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.Serializable;

public class Artifact implements Serializable {
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String packaging;

    public Artifact(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, null);
    }

    public Artifact(String groupId, String artifactId, String version, String packaging) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.packaging = packaging;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getPackaging() {
        return packaging;
    }

    public static class Parser {

        public Artifact parseXML(Node artifactNode) {
            Element artifactElement = (Element) artifactNode;

            String groupId = artifactElement.getElementsByTagName("groupId").item(0).getTextContent();
            String artifactId = artifactElement.getElementsByTagName("artifactId").item(0).getTextContent();
            String version = artifactElement.getElementsByTagName("version").item(0).getTextContent();

            return new Artifact(groupId, artifactId, version);
        }
    }
}
