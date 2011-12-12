package org.entraides.web;

import com.google.inject.*;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.entraides.service.IServiceUser;
import org.entraides.service.ServiceUser;

/**
 * Dans cette classe, les différents binding de la couche web sont ajoutés au fur et à mesure de l'ajout de
 * fonctionnalités
 */
public class ModuleEntraidesWicket extends AbstractModule{

    @Override
    protected void configure() {
        bind(IServiceUser.class).to(ServiceUser.class);
    }

    @Provides
    @Inject
    @Singleton
    protected WebApplication buildApplication(ApplicationWicket app, Injector injector){
        app.getComponentInstantiationListeners().add(new GuiceComponentInjector(app, injector));
        return app;
    }
}
