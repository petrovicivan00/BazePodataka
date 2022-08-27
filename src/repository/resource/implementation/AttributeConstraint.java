package src.repository.resource.implementation;

import lombok.Data;
import lombok.ToString;
import src.observer.Notification;
import src.observer.Subscriber;
import src.repository.resource.DBNode;
import src.repository.resource.DBNodeComposite;
import src.repository.resource.enums.ConstraintType;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
public abstract class AttributeConstraint extends DBNode {

    private ConstraintType constraintType;

    public AttributeConstraint(String name, DBNode parent, ConstraintType constraintType) {
        super(name, parent);
        this.constraintType = constraintType;
    }
}

