package org.entraides.web.fwk.bootstrap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * Utilisé pour bénéficier des classes de bootstrap dans les formulaires. Pas encore convaincu par bootstrap, en raison
 * d'un code html pas très propre; pour pouvoir mettre un style d'erreur sur un input, il faut au préalable avoir
 * placé un div autour. Normalement le code html n'est pas sensé avoir de balises aussi spécifiques.
 */
public class ClearFixBorder extends Border{

    public ClearFixBorder(String id) {
        super(id);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        FormComponent formComponent = visitChildren(FormComponent.class, new IVisitor<Component, FormComponent>() {
            public void component(Component object, IVisit<FormComponent> formComponentIVisit) {
                FormComponent f = (FormComponent) object;
                if (!f.isValid()) {
                    formComponentIVisit.stop(f);
                }
            }
        });
        if(formComponent != null){
            add(new AttributeModifier("class", "clearfix error"));
        }else{
            add(new AttributeModifier("class", "clearfix"));
        }


    }
}
