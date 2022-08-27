package src.repository.resource.implementation;

import lombok.Data;
import lombok.ToString;
import src.repository.resource.DBNode;
import src.repository.resource.DBNodeComposite;


@Data
@ToString(callSuper = true)
public class InformationResource extends DBNodeComposite {

    public InformationResource(String name) {
        super(name, null);
    }

    @Override
    public void addChild(DBNode child) {

        if (child != null && child instanceof Entity) {
            Entity entity = (Entity) child;
            this.getChildren().add(entity);
        }
    }
}