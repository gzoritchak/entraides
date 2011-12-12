package org.entraides.web.fwk.bootstrap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Response;

/**
 * Gère l'ajout du code html nécessaire pour l'affichage correct des champs de formulaires avec bootstap.
 *
 * Ajoute automatiquement le label du input ainsi que le div encadrant la ligne. En cas d'erreur de validation,
 * la classe error est ajoutée.
 *
 *  div.clearfix error?
 *      > label
 *          > div.input
 *              > input.error?
 *
 * Ce code fait un peu hack, mais il est rendu nécessaire pas la structure particulière que twitter bootstrap.
 */
public class BootstrapClearFixBehaviour extends Behavior{

    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    /**
     * On entoure le composant par 2 div et un label
     * @param component
     */
    @Override
    public void beforeRender(Component component) {
        super.beforeRender(component);
        FeedbackMessages messages = component.getSession().getFeedbackMessages();
        String clearfix = messages.hasMessageFor(component) ? "clearfix error" : "clearfix";
        component.getResponse().write(
                "<div class=\"" + clearfix + "\">" +
                        "<label for=\"" + component.getMarkupId() + "\">" +
                        new StringResourceModel(component.getId() + ".label", component, null).getObject() +
                        "</label>" +
                    "<div class=\"input\">");
    }

    /**
     * En cas d'erreur sur le composant, on affiche le premier message.
     */
    @Override
    public void afterRender(Component component) {
        super.afterRender(component);
        FeedbackMessages messages = component.getSession().getFeedbackMessages();
        Response response = component.getResponse();
        if(messages.hasMessageFor(component)){
            IFeedbackMessageFilter filter = new ComponentFeedbackMessageFilter(component);
            response.write("<span class=\"help-inline\">" + messages.messages(filter).get(0).getMessage() + "</span>");
        }
        response.write(
                    "</div>" +
                "</div>");
    }
}
