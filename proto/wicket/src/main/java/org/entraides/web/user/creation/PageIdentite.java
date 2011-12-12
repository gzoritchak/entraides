package org.entraides.web.user.creation;

import com.google.inject.Inject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.entraides.service.IServiceUser;
import org.entraides.web.fwk.bootstrap.BootstrapClearFixBehaviour;
import org.entraides.web.fwk.templating.OneColPage;

import java.io.Serializable;

public class PageIdentite extends OneColPage {

    @Inject
    IServiceUser serviceUser;

    PasswordTextField pass1;
    PasswordTextField pass2;

    /**
     * Pas beaucoup de données => on sérialise dans la session.
     */
    private static class IdentiteData implements Serializable {
        String email;
        String login;
        String pass1;
        String pass2;
    }

    public PageIdentite(PageParameters pp) {
        super(pp);

        IdentiteData data = new IdentiteData();
        data.email = pp.get("email").toOptionalString();

        StatelessForm<IdentiteData> form = new StatelessForm<IdentiteData>("form",
                new CompoundPropertyModel<IdentiteData>(data)) {
            @Override
            protected void onSubmit() {
                serviceUser.log("Soumission formulaire identité");
                setResponsePage(PageProfilUpdate.class);
            }
        };

        form.add(new TextField("email").setEnabled(false));
        form.add(new TextField("login"));
        form.add(pass1 = new PasswordTextField("pass1"));
        form.add(pass2 = new PasswordTextField("pass2"));
        form.add(new EqualPasswordInputValidator(pass1, pass2));
        form.add(new AjaxFallbackButton("submit", form) {
            @Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) { target.add(form); }
            @Override protected void onError(AjaxRequestTarget target, Form<?> form) { target.add(form); }
        });
        form.visitFormComponents(new IVisitor<FormComponent<?>, Object>() {
            public void component(FormComponent<?> object, IVisit<Object> objectIVisit) {
                if (object instanceof TextField)
                    object.add(new BootstrapClearFixBehaviour());
            }
        });

        add(form);
    }
}
