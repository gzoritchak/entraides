package org.entraides.web.user.creation;

import com.google.inject.Inject;
import com.visural.wicket.behavior.inputhint.InputHintBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.entraides.service.IServiceUser;
import org.entraides.web.fwk.bootstrap.BootStrapFieldDecorator;
import org.entraides.web.fwk.bootstrap.ClearFixBorder;
import org.entraides.web.fwk.templating.OneColPage;


public class PageInscription extends OneColPage {

    @Inject
    IServiceUser serviceUser;

    String email;

    public PageInscription(PageParameters pp) {
        super(pp);
        final TextField<String> field = new TextField<String>("email",
                new PropertyModel<String>(this, "email"));

        StatelessForm<?> form = new StatelessForm("form") {

            @Override
            protected void onSubmit() {
                serviceUser.log("Création user pour : " + field.getDefaultModelObject());
                setResponsePage(PageIdentite.class, new PageParameters().add("email", email));
            }
        };

        form.add(new AjaxFallbackButton("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) { target.add(form); }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) { target.add(form); }

        });


        field.add(new InputHintBehavior(form, "votre email", "color: #aaa;")); //ajout par jquery d'un input hint
        field.add(EmailAddressValidator.getInstance());
        field.add(new BootStrapFieldDecorator()); //utilisation des css bootstrap pour message d'erreur.
        field.setRequired(true);

        ClearFixBorder clearFixBorder = new ClearFixBorder("border");
        clearFixBorder.add(field);
        form.add(clearFixBorder);
        add(form);
    }
}
