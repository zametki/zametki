package com.github.zametki.component.help;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.BasePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@MountPath("/about")
public class AboutPage extends BasePage {

    public AboutPage(PageParameters pp) {
        super(pp);
        setTitleAndDesc("О сайте", "О сайте zametki.org");
    }

}
