package no.koteng.appstore;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

public class StartPage extends WebPage {

    List<Application> applicationList = new ArrayList<Application>();

    public StartPage(PageParameters parameters) {
        super(parameters);

        applicationList.add(new Application("Sagittis dapibus", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "application.png", new Artifact("log4j", "log4j", null, "jar")));
        applicationList.add(new Application("Condimentum tincidunt", "Donec molestie placerat libero, eget lobortis velit cursus vitae", "pumpkin.png", new Artifact("log4j", "log4j", null, "jar")));

        initStartPage();
    }

    private void initStartPage() {
        ListView<Application> applicationListView = new ListView<Application>("applicationListView", applicationList) {
            @Override
            protected void populateItem(ListItem<Application> item) {
                Application app = item.getModelObject();
                item.add(new ApplicationPanel(app));
            }
        };

        add(applicationListView);
    }
}
