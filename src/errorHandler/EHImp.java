package src.errorHandler;

import src.core.ErrorHandler;
import src.observer.Subscriber;

import java.util.ArrayList;

public class EHImp implements ErrorHandler {

    ArrayList<Subscriber> subscribers;

    @Override
    public void generateError(Type errorType) {

        if (errorType == Type.INVALID_QUERY) {
            notifySubscribers(new Error(2, "Query is not valid. Try: \"var query = new Query(\"table_name\") \" ", "Query"));
        }
        if (errorType == Type.COLUMN_NOT_FOUND) {
            notifySubscribers(new Error(3, "No column found ", "Column not found"));
        }
        if (errorType == Type.WRONG_FUNCTION_NAME) {
            notifySubscribers(new Error(3, "Wrong function name", "Wrong parameters"));
        }
        if (errorType == Type.WRONG_PARAMETERS) {
            notifySubscribers(new Error(3, "Wrong parameters", "Wrong parameters"));
        }
        if (errorType == Type.SYNTAX_ERROR) {
            notifySubscribers(new Error(1, "You're missing some characters from the statement. Could be brackets.", "Syntax error"));
        }
    }

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
