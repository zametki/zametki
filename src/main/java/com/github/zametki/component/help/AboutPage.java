package com.github.zametki.component.help;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.BasePage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;

@MountPath("/about")
public class AboutPage extends BasePage {

    public AboutPage(PageParameters pp) {
        super(pp);
        setTitleAndDesc("О сайте", "О сайте zametki.org");
    }

    public static class Link extends BookmarkablePageLink<Void> {
        public Link(@NotNull String id) {
            super(id, AboutPage.class);
        }
    }
}
