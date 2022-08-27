package src.observer.implementation;

import src.observer.Publisher;
import src.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class PublisherImp implements Publisher {

    private List<Subscriber> subscribers;

    public void addSubscriber(Subscriber sub) {

        if (sub == null)
            return;
        if (this.subscribers == null)
            this.subscribers = new ArrayList<>();
        if (this.subscribers.contains(sub))
            return;
        this.subscribers.add(sub);
    }

    public void removeSubscriber(Subscriber sub) {

        if (sub == null || this.subscribers == null || !this.subscribers.contains(sub))
            return;
        this.subscribers.remove(sub);
    }

    public void notifySubscribers(Object event) {

        if (event == null || this.subscribers == null || this.subscribers.isEmpty())
            return;

        for (Subscriber listener : subscribers) {
            listener.update(event);
        }
    }
}
