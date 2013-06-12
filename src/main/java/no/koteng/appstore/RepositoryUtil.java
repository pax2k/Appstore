package no.koteng.appstore;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUtil implements Serializable {
    private final String nexusURL = "http://projects.knowit.no/nexus/";
    private final String serviceURL = "service/local/lucene/search?";
    private final String nexusArtifactUrl = "service/local/artifact/maven/";
    private final String groupId;
    private final String artifactId;
    private final String packaging;

    public static enum REPOSITORIES {
        KNOWIT_HOSTED_REPO("knowit-hosted-repo"),
        ATLASSIAN_M2_REPO("atlassian-m2-repository"),
        CENTRAL("central");

        private String name;

        private REPOSITORIES(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public RepositoryUtil(String groupId, String artifactId, String packaging) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.packaging = packaging;
    }

    public String getUrlToAtifacts(String repositoryId) {
        return String.format(nexusURL + serviceURL + "g=%s&a=%s&p=%s&repositoryId=%s", groupId, artifactId, packaging, repositoryId);
    }

    public String getLatestArtifactURL(String repositoryId) {
        return buildArtifactUrl(null, repositoryId);
    }

    public String getArtifactURL(String version, String repositoryId) {
        return buildArtifactUrl(version, repositoryId);
    }

    public List<Repository> fetchAndParseArtifacts() throws IOException, ParserConfigurationException, SAXException {
        List<Repository> repositoryList = new ArrayList<Repository>();
        for (REPOSITORIES repo_VALUES : REPOSITORIES.values()) {
            Repository repository = fetchBuildInformation(repo_VALUES);

            if (repository != null) {
                repositoryList.add(repository);
            }
        }

        return repositoryList;
    }

    private String buildArtifactUrl(String version, String repositoryId) {
        if (version == null) {
            version = "LATEST";
        }

        return String.format(nexusURL + nexusArtifactUrl + "redirect?r=%s&g=%s&a=%s&v=%s", repositoryId, groupId, artifactId, version);
    }

    private Repository fetchBuildInformation(REPOSITORIES path) throws IOException, ParserConfigurationException, SAXException {
        Repository repository;

        URL url = new URL(getUrlToAtifacts(path.getName()));
        InputStream input = url.openStream();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(input);
        doc.getDocumentElement().normalize();

        Node totalCount = doc.getElementsByTagName("searchNGResponse").item(0);
        Element totalCountElement = (Element) totalCount;
        String count = totalCountElement.getElementsByTagName("totalCount").item(0).getTextContent();

        if (Integer.parseInt(count) == 0) {
            return null;
        }

        repository = new Repository.Parser().parseXML(doc);
        input.close();

        return repository;
    }

}
