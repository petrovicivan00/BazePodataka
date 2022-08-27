package src.repository.resource.implementation;

import lombok.Data;
import lombok.ToString;
import src.repository.resource.DBNode;
import src.repository.resource.DBNodeComposite;

@Data
@ToString(callSuper = true)
public class Entity extends DBNodeComposite {

    public Entity(String name, DBNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Attribute) {
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }
    }
}