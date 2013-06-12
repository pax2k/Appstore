package no.koteng.appstore;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class RepoPage extends WebPage {
    private static final long serialVersionUID = 1L;

    private final RepositoryUtil repositoryUtil;

    public RepoPage(final PageParameters parameters) throws ParserConfigurationException, SAXException, IOException {
        super(parameters);

        PageParameters pageParameters = getPageParameters();
        String applicationName = pageParameters.get(ApplicationPanel.APPLICATION_NAME).toString();
        String groupId = pageParameters.get(ApplicationPanel.GROUP_ID).toString();
        String artifactId = pageParameters.get(ApplicationPanel.ARTIFACT_ID).toString();
        String packaging = pageParameters.get(ApplicationPanel.PACKAGING).toString();

        repositoryUtil = new RepositoryUtil(groupId, artifactId, packaging);

        Label header = new Label("applicationName", applicationName);
        add(header);

        ListView<Repository> listView = new ListView<Repository>("repoPanels", repositoryUtil.fetchAndParseArtifacts()) {
            protected void populateItem(ListItem<Repository> item) {
                Repository repoItem = item.getModelObject();
                item.add(new RepoPanel(repoItem, repositoryUtil));
            }
        };

        add(listView);
    }
}
