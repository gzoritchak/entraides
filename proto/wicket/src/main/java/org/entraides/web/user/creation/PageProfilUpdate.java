package org.entraides.web.user.creation;

import com.google.inject.Inject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.entraides.service.IServiceUser;
import org.entraides.web.fwk.bootstrap.BootstrapClearFixBehaviour;
import org.entraides.web.fwk.templating.OneColPage;

import java.io.Serializable;


public class PageProfilUpdate extends OneColPage {

    @Inject
    IServiceUser serviceUser;

    private static class ProfilData implements Serializable {
        String localisation;
        String bio;
        String autres;
    }

    public PageProfilUpdate(PageParameters pp) {
        super(pp);

        ProfilData data = new ProfilData();

        StatelessForm<ProfilData> form = new StatelessForm<ProfilData>("form", new CompoundPropertyModel<ProfilData>(data)) {

            @Override
            protected void onSubmit() {
                serviceUser.log("Soumission modification profil");
            }
        };
        form.add(new TextField("localisation"));
        form.add(new TextArea("bio"));
        form.add(new TextField("autres"));

        form.add(new AjaxFallbackButton("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
        add(form);

        form.visitFormComponents(new IVisitor<FormComponent<?>, Object>() {
            public void component(FormComponent<?> object, IVisit<Object> objectIVisit) {
                if (object instanceof TextField ||
                                object instanceof TextArea
                        )
                    object.add(new BootstrapClearFixBehaviour());
            }
        });

    }
}
