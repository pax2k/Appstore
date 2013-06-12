package no.koteng.appstore;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication {

    @Override
    public Class<? extends WebPage> getHomePage() {
        return StartPage.class;
    }

    @Override
    public void init() {
        super.init();
    }
}
