package org.entraides.web.fwk.templating;

import com.jquery.JQueryResourceReference;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Insertion des css et script partagés dans tout le site
 */
public abstract class BasePage extends WebPage {

    protected BasePage() {
    }

    protected BasePage(IModel<?> model) {
        super(model);
    }

    public BasePage(PageParameters pp) {
        super(pp);
    }

    /**
     * Insertion de toutes les css et fichiers javascript nécessaires sur toutes les pages.
     * bootstrap.css    : grid pour assurer des alignements constants,
     *
     * L'inclusion des styles par wicket permet de générer un numéro de version (checksum) qui, en paramétrant
     * la gestion des headers garantie une mise en cache à vie de la css dans le navigateur et supprime des requêtes.
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference(new PackageResourceReference(BasePage.class, "bootstrap.css"));
        response.renderJavaScriptReference(new JQueryResourceReference(JQueryResourceReference.Version.V1_6_3));
    }
}
