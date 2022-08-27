package src.core;

import src.errorHandler.Type;
import src.observer.Publisher;

public interface ErrorHandler extends Publisher {
    void generateError(Type errorType);
}
