package net.betzel.osgi.karaf.vaadin.service;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.*;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.collections.WeakMapChangeListener;
import net.betzel.osgi.karaf.vaadin.api.VaadinComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Theme("valo")
@Push(PushMode.MANUAL)
public class VaadinUI extends UI {

    private HashMap<String, Component> componentInstances;
    private ObservableMap<String, VaadinComponent> observableVaadinComponents;
    private MapChangeListener<String, VaadinComponent> vaadinComponentChangeListener;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        observableVaadinComponents = VaadinSession.getCurrent().getAttribute(ObservableMap.class);
        componentInstances = VaadinSession.getCurrent().getAttribute(HashMap.class);

        VerticalLayout verticalLayout = new VerticalLayout();
        TextField name = new TextField();
        name.setCaption("Type your name here:");
        Button button = new Button("Click me");
        button.addClickListener(e -> {
            verticalLayout.addComponent(new Label("Thank you " + name.getValue() + ", Static Vaadin on OSGi works!"));
        });
        verticalLayout.addComponents(name, button);
        for(Map.Entry<String, VaadinComponent> entry : observableVaadinComponents.entrySet()) {
            Component component = entry.getValue().getInstance(entry.getKey());
            componentInstances.put(entry.getKey(), component);
            verticalLayout.addComponent(component);
        }
        setContent(verticalLayout);

        vaadinComponentChangeListener = change -> {
            if (change.wasAdded()) {
                UI ui = getUI();
                if (Objects.nonNull(ui) && ui.isAttached()) {
                    ui.access(() -> {
                        Component component = change.getValueAdded().getInstance(change.getKey());
                        componentInstances.put(change.getKey(), component);
                        verticalLayout.addComponent(component);
                        ui.push();
                    });
                }
            } else {
                UI ui = getUI();
                if (Objects.nonNull(ui) && ui.isAttached()) {
                    ui.access(() -> {
                        Component component = componentInstances.remove(change.getKey());
                        verticalLayout.removeComponent(component);
                        ui.push();
                    });
                }
            }
        };
        observableVaadinComponents.addListener(new WeakMapChangeListener(vaadinComponentChangeListener));

        UI.getCurrent().addDetachListener((DetachListener) event -> observableVaadinComponents.removeListener(vaadinComponentChangeListener));

    }

}