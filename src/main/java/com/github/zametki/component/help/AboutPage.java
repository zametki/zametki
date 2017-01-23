package com.github.zametki.component.help;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.BasePage;

@MountPath("/about")
public class AboutPage extends BasePage {
    public static final String FORMAT_ANCHOR = "format";

    public AboutPage() {
        setTitleAndDesc("О сайте", "О сайте zametki.org");
    }

}
