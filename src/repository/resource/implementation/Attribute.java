package src.repository.resource.implementation;

import lombok.Data;
import lombok.ToString;
import src.repository.resource.DBNode;
import src.repository.resource.DBNodeComposite;
import src.repository.resource.enums.AttributeType;

@Data
@ToString(callSuper = true)
public class Attribute extends DBNodeComposite {

    private AttributeType attributeType;
    private int length;
    private Attribute inRelationWith;

    public Attribute(String name, DBNode parent) {
        super(name, parent);
    }

    public Attribute(String name, DBNode parent, AttributeType attributeType, int length) {
        super(name, parent);
        this.attributeType = attributeType;
        this.length = length;
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof AttributeConstraint) {
            AttributeConstraint attributeConstraint = (AttributeConstraint) child;
            this.getChildren().add(attributeConstraint);
        }
    }
}