package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.form.InputArea;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.AbstractListProvider;
import com.github.zametki.util.TextUtils;
import com.github.zametki.util.UDate;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * Страница со списком персональных заметок.
 */
@MountPath("/lenta")
public class LentaPage extends BaseUserPage {
    public LentaPage() {

        WebMarkupContainer lenta = new ContainerWithId("lenta");
        add(lenta);

        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        LentaProvider provider = new LentaProvider();

        lenta.add(new DataView<ZametkaId>("zametka", provider) {
            @Override
            protected void populateItem(Item<ZametkaId> item) {
                ZametkaId id = item.getModelObject();
                Zametka z = Context.getZametkaDbi().getById(id);
                if (z == null) {
                    item.setVisible(false);
                    return;
                }
                item.add(new Label("content", z.content));
            }
        });

        InputArea textField = new InputArea("text", "");
        form.add(textField);

        form.add(new ValidatingJsAjaxSubmitLink("save_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                String content = textField.getInputString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                Zametka z = new Zametka();
                z.userId = WebUtils.getUserOrRedirectHome().id;
                z.creationDate = UDate.now();
                z.content = content;
                Context.getZametkaDbi().create(z);

                textField.clearInput();
                target.add(form);

                provider.detach();
                target.add(lenta);
            }
        });

    }

    private static class LentaProvider extends AbstractListProvider<ZametkaId> {

        @Override
        public List<ZametkaId> getList() {
            return Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
        }

        @Override
        public IModel<ZametkaId> model(ZametkaId z) {
            return Model.of(z);
        }
    }
}
