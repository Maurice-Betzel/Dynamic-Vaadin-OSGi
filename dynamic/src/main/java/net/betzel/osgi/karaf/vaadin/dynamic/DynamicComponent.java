package net.betzel.osgi.karaf.vaadin.dynamic;

import com.vaadin.ui.*;
import net.betzel.osgi.karaf.vaadin.api.VaadinComponent;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static net.betzel.osgi.karaf.vaadin.api.VaadinComponent.COMPONENT_PROPERTY;

@org.osgi.service.component.annotations.Component(property = COMPONENT_PROPERTY + "=Click me once more")
public class DynamicComponent implements VaadinComponent<Component> {

    private WeakReference<Component> instance;

    @Override
    public Component getInstance() {
        if(Objects.isNull(instance)) constructComponent("Click me again");
        return instance.get();
    }

    @Override
    public Component getInstance(String caption) {
        if(Objects.isNull(instance)) constructComponent(caption);
        return instance.get();
    }

    private void constructComponent(String caption) {
        VerticalLayout verticalLayout = new VerticalLayout();
        TextField name = new TextField();
        name.setCaption("Type your name here once more:");
        Button button = new Button(caption);
        button.addClickListener(e -> {
            verticalLayout.addComponent(new Label("Thank you " + name.getValue() + ", Dynamic Vaadin on OSGi works! Now stop the dynamic bundle, watch the change and start it again!"));
        });
        verticalLayout.addComponents(name, button);
        instance = new WeakReference<Component>(verticalLayout);
    }

}