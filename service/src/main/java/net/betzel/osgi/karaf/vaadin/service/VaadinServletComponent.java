package net.betzel.osgi.karaf.vaadin.service;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import net.betzel.osgi.karaf.vaadin.api.VaadinComponent;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.Map;

import static net.betzel.osgi.karaf.vaadin.api.VaadinComponent.COMPONENT_PROPERTY;

@Component(service = VaadinServlet.class, scope = ServiceScope.SINGLETON)
@WebServlet(urlPatterns = "/service/*", name = "VaadinUI", asyncSupported = true, initParams = {@WebInitParam(name = "org.atmosphere.websocket.suppressJSR356", value = "true")})
@VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
public class VaadinServletComponent extends VaadinServlet implements SessionInitListener, SessionDestroyListener {

    final ObservableMap<String, VaadinComponent> observableVaadinComponents = FXCollections.synchronizedObservableMap(FXCollections.observableMap(new HashMap()));

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        VaadinSession.getCurrent().setAttribute(ObservableMap.class, observableVaadinComponents);
        VaadinSession.getCurrent().setAttribute(HashMap.class, new HashMap<String, com.vaadin.ui.Component>());
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        VaadinSession.getCurrent().setAttribute(ObservableMap.class, null);
        Map map = VaadinSession.getCurrent().getAttribute(HashMap.class);
        map.clear();
        VaadinSession.getCurrent().setAttribute(HashMap.class, null);
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, service = VaadinComponent.class, policy = ReferencePolicy.DYNAMIC)
    void bindVaadinComponent(VaadinComponent<?> vaadinComponent, Map<String, ?> map, ServiceReference<?> serviceReference) {
        String property = (String) map.get(COMPONENT_PROPERTY);
        observableVaadinComponents.put(property, vaadinComponent);
    }

    void unbindVaadinComponent(VaadinComponent<?> a, Map<String, Object> map) {
        String property = (String) map.get(COMPONENT_PROPERTY);
        observableVaadinComponents.remove(property);
    }

}