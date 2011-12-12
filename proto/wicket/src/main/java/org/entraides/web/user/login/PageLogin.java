package org.entraides.web.user.login;

import com.google.inject.Inject;
import com.visural.wicket.behavior.inputhint.InputHintBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.entraides.service.IServiceUser;
import org.entraides.web.fwk.bootstrap.BootStrapFieldDecorator;
import org.entraides.web.fwk.templating.OneColPage;

import java.io.Serializable;


public class PageLogin extends OneColPage {

    @Inject
    IServiceUser serviceUser;

    private static class LoginData implements Serializable{
        String login;
        String pass;

        @Override
        public String toString() {
            return "LoginData{" +
                    "login='" + login + '\'' +
                    '}';
        }
    }

    public PageLogin(PageParameters pp) {
        super(pp);
        
        final TextField<String> login = new TextField<String>("login");
        final PasswordTextField pass = new PasswordTextField("pass");

        StatelessForm<LoginData> form = new StatelessForm<LoginData>("form",
                new CompoundPropertyModel<LoginData>(new LoginData())) {

            @Override
            protected void onSubmit() {
                serviceUser.log("Tentative login : " + getDefaultModelObject());
            }
        };

        form.add(new AjaxFallbackButton("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) { target.add(form); }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) { target.add(form); }

        });


        login.add(new InputHintBehavior(form, "votre identifiant", "color: #aaa;"));
        login.add(new BootStrapFieldDecorator());
        login.setRequired(true);

        pass.add(new InputHintBehavior(form, "votre mot de passe", "color: #aaa;"));
        pass.add(new BootStrapFieldDecorator());
        pass.setRequired(true);

        form.add(login);
        form.add(pass);
        add(form);
    }
}
