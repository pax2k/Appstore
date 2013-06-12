package no.koteng.appstore;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final String id;
    private final String name;
    private final List<Artifact> artifactList;

    public Repository(String id, String name, List<Artifact> artifactList) {
        this.id = id;
        this.name = name;
        this.artifactList = artifactList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Artifact> getArtifactList() {
        return artifactList;
    }

    public static class Parser {

        public Repository parseXML(Document doc) {
            Node repoInformation = doc.getElementsByTagName("repoDetails").item(0);
            Element repoInformationElement = (Element) repoInformation;

            String repositoryId = repoInformationElement.getElementsByTagName("repositoryId").item(0).getTextContent();
            String repositoryName = repoInformationElement.getElementsByTagName("repositoryName").item(0).getTextContent();

            List<Artifact> artifacts = new ArrayList<Artifact>();
            NodeList artifactsNodeList = doc.getElementsByTagName("artifact");

            for (int i = 0; i < artifactsNodeList.getLength(); i++) {
                artifacts.add(new Artifact.Parser().parseXML(artifactsNodeList.item(i)));
            }

            return new Repository(repositoryId, repositoryName, artifacts);
        }
    }
}
