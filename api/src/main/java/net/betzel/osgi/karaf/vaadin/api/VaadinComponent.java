package net.betzel.osgi.karaf.vaadin.api;

import com.vaadin.ui.Component;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface VaadinComponent<T extends Component> {

    String COMPONENT_PROPERTY = "property";

    T getInstance();

    T getInstance(String caption);

}
