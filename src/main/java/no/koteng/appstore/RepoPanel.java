package no.koteng.appstore;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class RepoPanel extends Panel {

    public RepoPanel(final Repository repository, final RepositoryUtil repositoryUtil) {
        super("repoPanel");

        add(new Label("repoName", repository.getName()));
        add(new ExternalLink("artifactLatest", repositoryUtil.getLatestArtifactURL(repository.getId()), "here"));

        ListView<Artifact> listView = new ListView<Artifact>("listViewArtifact", repository.getArtifactList()) {
            protected void populateItem(ListItem<Artifact> item) {
                final Artifact artifact = item.getModelObject();
                final String name = artifact.getArtifactId();
                final String version = artifact.getVersion();
                final String displayName = name + "(" + version + ")";

                item.add(new ExternalLink("artifactLink", repositoryUtil.getArtifactURL(version, repository.getId()), displayName));
            }
        };

        add(listView);
    }
}
