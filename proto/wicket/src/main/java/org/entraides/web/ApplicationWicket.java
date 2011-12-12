package org.entraides.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.entraides.web.user.creation.PageIdentite;
import org.entraides.web.user.creation.PageInscription;
import org.entraides.web.user.creation.PageProfilUpdate;
import org.entraides.web.user.login.PageLogin;

/**
 * Définition de l'application au sens wicket. Gère le mapping des pages (url).
 *
 * Pour démarrer simplement le serveur web, lancer org.entraides.Start#main(String[])
 */
public class ApplicationWicket extends WebApplication {

    @Override
    public Class<PageAccueil> getHomePage() {
        return PageAccueil.class;
    }

    @Override
    protected void init() {
        mountPage("user/creation",              PageInscription.class);
        mountPage("user/creation/identite",     PageIdentite.class);
        mountPage("user/profil/update",         PageProfilUpdate.class);
        mountPage("user/login",                 PageLogin.class);
        adjustSettings();
    }

    private void adjustSettings() {
        // on supprime les tags wicket même en mode debug pour que les css bootstrap réagissent bien
        getMarkupSettings().setStripWicketTags(true);
    }
}
