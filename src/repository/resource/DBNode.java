package src.repository.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import src.observer.Publisher;
import src.observer.implementation.PublisherImp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DBNode extends PublisherImp {

    private String name;
    @ToString.Exclude
    private DBNode parent;
}
