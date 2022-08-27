package src.observer;

public interface Publisher {

    void addSubscriber(Subscriber sub);

    void removeSubscriber(Subscriber sub);

    void notifySubscribers(Object event);
}
