package org.entraides.web.fwk.bootstrap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.Response;

/**
 * Ajoute du code spécifique a bootstrap pour fournir le feedback en cas d'erreur de saisie
 */
public class BootStrapFieldDecorator extends Behavior {

    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    /**
     * On affiche le premier message correspondant au composant.
     */
    @Override
    public void afterRender(Component component) {
        FormComponent<?> f = (FormComponent<?>) component;
        Response response = component.getResponse();
        FeedbackMessages messages = f.getSession().getFeedbackMessages();
        if(messages.hasMessageFor(component)){
            IFeedbackMessageFilter filter = new ComponentFeedbackMessageFilter(component);
            response.write("<span class=\"help-inline\">" + messages.messages(filter).get(0).getMessage() + "</span>");
        }
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        FormComponent<?> f = (FormComponent<?>) component;
        if (!f.isValid()){
            String cl = tag.getAttribute("class");
            if(cl == null){
                tag.put("class", "error");
            }else{
                tag.put("class", "error " + cl);
            }
        }
    }
}
