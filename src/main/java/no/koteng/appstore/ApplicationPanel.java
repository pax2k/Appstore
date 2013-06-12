package no.koteng.appstore;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ApplicationPanel extends Panel {

    public static final String APPLICATION_NAME = "applicationName";
    public static final String GROUP_ID = "groupId";
    public static final String ARTIFACT_ID = "artifactId";
    public static final String PACKAGING = "packaging";

    public ApplicationPanel(final Application application) {
        super("applicationPanel");

        WebMarkupContainer webMarkupContainer = new WebMarkupContainer("appWrapper");
        webMarkupContainer.setOutputMarkupId(true);
        webMarkupContainer.add(new AjaxEventBehavior("onclick") {

            @Override
            protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
                PageParameters parameters = new PageParameters();
                parameters.add(APPLICATION_NAME, application.getApplicationName());
                parameters.add(GROUP_ID, application.getArtifact().getGroupId());
                parameters.add(ARTIFACT_ID, application.getArtifact().getArtifactId());
                parameters.add(PACKAGING, application.getArtifact().getPackaging());

                setResponsePage(RepoPage.class, parameters);
            }
        });

        webMarkupContainer.add(new Image("applicationImage", application.getImageUrl()));
        webMarkupContainer.add(new Label("applicationName", application.getApplicationName()));
        webMarkupContainer.add(new Label("applicationDescription", application.getDescription()));

        add(webMarkupContainer);
    }
}
