package src.core;

import src.repository.resource.DBNode;
import src.repository.resource.data.Row;

import java.util.List;

public interface Repository {

    DBNode getSchema();
    List<Row> get(String from);
}
