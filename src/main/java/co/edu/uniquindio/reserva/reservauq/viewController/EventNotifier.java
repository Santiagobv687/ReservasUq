package co.edu.uniquindio.reserva.reservauq.viewController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventNotifier {
    private static EventNotifier instance;
    private PropertyChangeSupport support;

    private EventNotifier() {
        support = new PropertyChangeSupport(this);
    }

    public static EventNotifier getInstance() {
        if (instance == null) {
            instance = new EventNotifier();
        }
        return instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void notify(String eventType, Object oldValue, Object newValue) {
        support.firePropertyChange(eventType, oldValue, newValue);
    }
}
