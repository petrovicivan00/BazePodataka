package src.observer;

import lombok.AllArgsConstructor;
import lombok.Data;
import src.observer.enums.NotificatonCode;

@Data
@AllArgsConstructor
public class Notification {

    private NotificatonCode code;
    private Object data;
}
